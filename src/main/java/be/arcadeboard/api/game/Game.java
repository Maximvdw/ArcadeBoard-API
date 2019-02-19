package be.arcadeboard.api.game;

import be.arcadeboard.api.ArcadeBoardPlugin;
import be.arcadeboard.api.annotations.*;
import be.arcadeboard.api.game.events.GameEndEvent;
import be.arcadeboard.api.game.events.GamePlayerJoinEvent;
import be.arcadeboard.api.game.events.GamePlayerLeaveEvent;
import be.arcadeboard.api.game.events.GameStartEvent;
import be.arcadeboard.api.game.graphics.Canvas;
import be.arcadeboard.api.implementation.UserInterfaceHandler;
import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.player.events.KeyDownEvent;
import be.arcadeboard.api.player.events.KeyUpEvent;
import be.arcadeboard.api.player.events.MouseClickEvent;
import be.arcadeboard.api.player.events.MouseMoveEvent;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Game class
 * <p>
 * Extend this class to create your game
 * Define the type of canvas you want to use
 * as the parametrized type
 * <p>
 * ex.
 * "... extends Game<CharacterCanvas>"
 */
public abstract class Game<T extends Canvas> extends GameInformation implements Runnable, GameState {
    private UUID uuid = UUID.randomUUID();
    private float currentFps = 0;
    private float fps = 0;
    private long lastFPSWarning = 0;
    private T canvas = null;
    private boolean running = false;

    // Actual implementation showing the canvas
    private UserInterfaceHandler<T> userInterfaceHandler = null;

    // ArcadeBoard plugin interface
    private ArcadeBoardPlugin plugin = null;

    // Configuration
    private FileConfiguration config = null;
    private File configFile = null;
    private File dataFolder = null;

    // Ticks and state
    private long ticks = 0L;
    private GameState gameState = this;
    private long gameStateTicks = 0L;

    // Ordered player lists
    private List<GamePlayer> players = new ArrayList<GamePlayer>();
    private Map<GamePlayer, GamePlayerState> playerStates = new HashMap<GamePlayer, GamePlayerState>();
    private Map<GamePlayer, Long> playerStateTicks = new HashMap<GamePlayer, Long>();

    // Player data specific to game
    private Map<GamePlayer, T> playerCanvas = new ConcurrentHashMap<GamePlayer, T>();
    private Class<T> canvasClass;

    // Listeners
    private List<MouseClickListener> mouseClickListeners = new ArrayList<MouseClickListener>();
    private List<MouseMoveListener> mouseMoveListeners = new ArrayList<MouseMoveListener>();
    private List<KeyDownListener> keyDownListeners = new ArrayList<KeyDownListener>();
    private List<KeyUpListener> keyUpListeners = new ArrayList<KeyUpListener>();

    // Game options
    private Map<GameOption, Object> gameOptions = new HashMap<GameOption, Object>();
    private Map<String, Object> customGameSettings = new TreeMap<String, Object>();

    // Running task
    private int task = -1;

    public Game(ArcadeBoardPlugin plugin) {
        if (plugin != null) {
            setPlugin(plugin);
        }
        setOption(GameOption.TPS, 20);                                      // Default TPS (20 ticks per second)
        setOption(GameOption.ENABLED, true);                                // Game is default enabled
        setOption(GameOption.GLOBAL_CANVAS, true);                          // Game uses one canvas by default
        setOption(GameOption.MINIMUM_PLAYERS, 1);                           // Game has a minimum amount of players of 1
        setOption(GameOption.MAXIMUM_PLAYERS, 1);                           // Game has a maximum amount of players of 1
        setOption(GameOption.BACKGROUND, GameOption.Choice.OPT_OUT);
        setOption(GameOption.VISIBLE,true);

        setGameClass((Class<? extends Game>) this.getClass().getSuperclass());

        // Load game information (for internal usage)
        Class<?> annotatedClass = this.getClass();
        while (annotatedClass != null) {
            Annotation[] annotations = annotatedClass.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof GameName) {
                    setName(((GameName) annotation).value());
                    if (getDisplayName().isEmpty()) {
                        setDisplayName(getName());
                    }
                } else if (annotation instanceof GameDisplayName) {
                    setDisplayName(((GameDisplayName) annotation).value());
                } else if (annotation instanceof GameControls) {
                    setControls(((GameControls) annotation).value());
                } else if (annotation instanceof GameDescription) {
                    setDescription(((GameDescription) annotation).value());
                } else if (annotation instanceof GameAuthor) {
                    setAuthor(((GameAuthor) annotation).value());
                } else if (annotation instanceof GameVersion) {
                    setVersion(((GameVersion) annotation).value());
                }
            }
            annotatedClass = annotatedClass.getSuperclass();
        }
    }

    /**
     * Get canvas class
     *
     * @return canvas class
     */
    public final Class<? extends Canvas> getCanvasClass() {
        if (canvasClass == null) {
            canvasClass = (Class<T>)
                    ((ParameterizedType) getClass()
                            .getGenericSuperclass())
                            .getActualTypeArguments()[0];
        }
        return canvasClass;
    }

    /**
     * Create a new canvas
     *
     * @param width  Width of the canvas
     * @param height Height of the canvas
     * @return canvas
     */
    private final T createCanvas(int width, int height) {
        try {
            T canvas = (T) getCanvasClass().newInstance();
            canvas.setHeight(height);
            canvas.setWidth(width);
            canvas.clear();
            return canvas;
        } catch (Exception e) {
            severe("Unable to create new drawing canvas using class: " + getCanvasClass().getCanonicalName());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Start the game
     *
     * @param plugin Plugin
     */
    public final void start(Plugin plugin) {
        if (getUserInterfaceHandler() == null)
            return;
        if (getPlugin() == null)
            return;

        GameStartEvent startEvent = new GameStartEvent(this);
        Bukkit.getPluginManager().callEvent(startEvent);
        if (startEvent.isCancelled()) {
            return;
        }

        onGameStart(startEvent);
        running = true;
        task = Bukkit.getScheduler().runTaskAsynchronously(plugin, this).getTaskId();
        getPlugin().getGameManager().addRunningGame(this);
    }

    /**
     * Stop the game
     */
    public final void stop() {
        GameEndEvent endEvent = new GameEndEvent(this);
        Bukkit.getPluginManager().callEvent(endEvent);
        if (endEvent.isCancelled()) {
            return;
        }
        onGameEnd(endEvent);
        running = false;
        Bukkit.getScheduler().cancelTask(task);
        List<GamePlayer> gamePlayers = new ArrayList<GamePlayer>(this.players);
        for (GamePlayer player : gamePlayers) {
            getUserInterfaceHandler().destroy(player);
        }
        players.clear();
        playerCanvas.clear();
        getPlugin().getGameManager().removeRunningGame(this);
    }

    /**
     * Add key release listener
     *
     * @param keyUpListener Key release listener
     */
    public final void addKeyReleaseListener(KeyUpListener keyUpListener) {
        keyUpListeners.add(keyUpListener);
    }

    /**
     * Add key listener
     *
     * @param keyListener key listener
     */
    public final void addKeyListener(KeyListener keyListener) {
        addKeyPressListener(keyListener);
        addKeyReleaseListener(keyListener);
    }

    /**
     * Add mouse move listener
     *
     * @param mouseMoveListener mouse move listener
     */
    public final void addMouseMoveListener(MouseMoveListener mouseMoveListener) {
        mouseMoveListeners.add(mouseMoveListener);
    }

    /**
     * Add key press listener
     *
     * @param keyDownListener Key press listener
     */
    public final void addKeyPressListener(KeyDownListener keyDownListener) {
        keyDownListeners.add(keyDownListener);
    }

    /**
     * Add key down listener
     *
     * @param keyDownListener Key down listener
     */
    public final void addKeyDownListener(KeyDownListener keyDownListener) {
        keyDownListeners.add(keyDownListener);
    }

    /**
     * Add mouse click listener
     *
     * @param mouseClickListener Mouse click listener
     */
    public final void addMouseClickListener(MouseClickListener mouseClickListener) {
        mouseClickListeners.add(mouseClickListener);
    }

    /**
     * Add mouse listener
     *
     * @param mouseListener Mouse lsitener
     */
    public final void addMouseListener(MouseListener mouseListener) {
        addMouseClickListener(mouseListener);
        addMouseMoveListener(mouseListener);
    }

    /**
     * Remove a mouse click listener
     *
     * @param mouseClickListener Mouse click listener
     */
    public final void removeMouseClickListener(MouseClickListener mouseClickListener) {
        if (mouseClickListeners.contains(mouseClickListener)) {
            mouseClickListeners.remove(mouseClickListener);
        }
    }

    public final List<KeyUpListener> getKeyUpListeners() {
        return keyUpListeners;
    }

    public final List<MouseClickListener> getMouseClickListeners() {
        return mouseClickListeners;
    }

    public final List<MouseMoveListener> getMouseMoveListeners() {
        return mouseMoveListeners;
    }

    public final List<KeyDownListener> getKeyDownListeners() {
        return keyDownListeners;
    }

    /**
     * Get current frames per second
     *
     * @return TPS
     */
    public final float getCurrentFPS() {
        return fps;
    }

    private final void setCurrentFPS(float fps) {
        this.fps = fps;
    }

    /**
     * Get player state
     *
     * @param gamePlayer game player
     * @return player state
     */
    public final GamePlayerState getPlayerState(GamePlayer gamePlayer) {
        return playerStates.get(gamePlayer);
    }

    /**
     * Get game state ticks
     *
     * @return game state ticks
     */
    public final long getGameStateTicks() {
        return gameStateTicks;
    }

    /**
     * Get player state ticks
     *
     * @param gamePlayer Game player
     * @return player state ticks
     */
    public final long getPlayerStateTicks(GamePlayer gamePlayer) {
        Long ticks = playerStateTicks.get(gamePlayer);
        if (ticks == null) {
            return -1;
        } else {
            return ticks;
        }
    }

    /**
     * Remove player state
     *
     * @param gamePlayer game player
     */
    public final void removePlayerState(GamePlayer gamePlayer) {
        if (playerStates.containsKey(gamePlayer)) {
            GamePlayerState currentState = playerStates.get(gamePlayer);
            if (getMouseMoveListeners().contains(currentState)) {
                mouseMoveListeners.remove(currentState);
            }
            if (getMouseClickListeners().contains(currentState)) {
                mouseClickListeners.remove(currentState);
            }
            if (getKeyDownListeners().contains(currentState)) {
                keyDownListeners.remove(currentState);
            }
            if (getKeyUpListeners().contains(currentState)) {
                keyUpListeners.remove(currentState);
            }
            playerStates.remove(gamePlayer);
            playerStateTicks.remove(gamePlayer);
        }
    }

    /**
     * Set player state
     *
     * @param gamePlayer game player state
     * @param state      player state
     */
    public final void setPlayerState(GamePlayer gamePlayer, GamePlayerState state) {
        removePlayerState(gamePlayer);
        playerStates.put(gamePlayer, state);
        playerStateTicks.put(gamePlayer, 0L);
    }

    /**
     * On game start
     *
     * @param event Game start event
     */
    public void onGameStart(GameStartEvent event) {
        // Can optionally be implemented by games
    }

    /**
     * On game end
     *
     * @param event Game end event
     */
    public void onGameEnd(GameEndEvent event) {
        // Can optionally be implemented by games
    }

    /**
     * On player leave
     *
     * @param event game player leave
     */
    public void onPlayerLeave(GamePlayerLeaveEvent event) {
        // Can optionally be implemented by games
    }

    /**
     * On player join
     *
     * @param event game player join
     */
    public void onPlayerJoin(GamePlayerJoinEvent event) {
        // Can optionally be implemented by games
    }

    /**
     * Internal loop
     */
    public final void run() {
        long lastFPSCalc = System.currentTimeMillis();
        int tps = getOptionInt(GameOption.TPS);
        while (isRunning()) {
            try {
                // Update game
                ticks++;
                if (System.currentTimeMillis() - lastFPSCalc >= 1000) {
                    fps = currentFps;
                    currentFps = 0;
                    lastFPSCalc = System.currentTimeMillis();
                }
                currentFps++;

                // Game loop
                gameStateTicks++;
                loop();

                // Handle update
                if (isRunning()) {
                    getUserInterfaceHandler().update(playerCanvas);
                    // Check FPS
                    if (tps - getCurrentFPS() > 2 && (ticks / tps) > 20) {
                        if (lastFPSWarning + 30 < (ticks / tps)) {
                            warning("Frame rate is low: " + getCurrentFPS());
                            lastFPSWarning = (ticks / tps);
                        }
                    }
                    // Wait for next frame
                    Thread.sleep((long) (1000. / tps));
                }
            } catch (Exception ex) {
                getUserInterfaceHandler().error(ex);
                stop(); // Games with errors are stopped to avoid spam
            }
        }
    }

    /**
     * Get canvas
     *
     * @return canvas
     */
    public final T getMainCanvas() {
        return canvas;
    }

    /**
     * Set canvas
     *
     * @param canvas canvas
     */
    private final void setMainCanvas(T canvas) {
        this.canvas = canvas;
    }

    /**
     * Is the game running
     *
     * @return running
     */
    public final boolean isRunning() {
        return running;
    }

    /**
     * Get canvas for player
     *
     * @param player Player to get canvas for
     * @return Player canvas
     */
    public final T getCanvas(GamePlayer player) {
        if (playerCanvas.containsKey(player))
            return playerCanvas.get(player);
        return null;
    }

    /**
     * Add a new player to the game
     *
     * @param gamePlayer Player to add
     */
    public final boolean addPlayer(final GamePlayer gamePlayer) {
        // Check if the player is in another game
        if (getPlugin().getGameManager().isPlaying(gamePlayer)) {
            return false;
        }

        if (getOptionBoolean(GameOption.GLOBAL_CANVAS)) {
            if (getMainCanvas() == null) {
                T newCanvas = createCanvas(getOptionInt(GameOption.SCREEN_WIDTH), getOptionInt(GameOption.SCREEN_HEIGHT));
                setMainCanvas(newCanvas);
            }
            playerCanvas.put(gamePlayer, getMainCanvas());
        } else {
            playerCanvas.put(gamePlayer, createCanvas(getOptionInt(GameOption.SCREEN_WIDTH), getOptionInt(GameOption.SCREEN_HEIGHT)));
        }
        players.add(gamePlayer);
        GamePlayerJoinEvent playerJoinEvent = new GamePlayerJoinEvent(this, gamePlayer);
        onPlayerJoin(playerJoinEvent);
        if (playerJoinEvent.isCancelled()) {
            playerCanvas.remove(gamePlayer);
            players.remove(gamePlayer);
            removePlayerState(gamePlayer);

            // Do not use minimum players here, there might be a lobby
            if (players.size() < 1) {
                stop();
            }
            return false;
        }
        // Any UI based implementation will have to be constructed on the main thread
        Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {
            public void run() {
                getUserInterfaceHandler().construct(gamePlayer);
            }
        });
        return true;
    }

    /**
     * Remove a player from the game
     *
     * @param gamePlayer player to remove
     * @return success
     */
    public final boolean removePlayer(GamePlayer gamePlayer) {
        GamePlayerLeaveEvent gamePlayerLeaveEvent = new GamePlayerLeaveEvent(this, gamePlayer);
        onPlayerLeave(gamePlayerLeaveEvent);

        if (players.contains(gamePlayer)) {
            players.remove(gamePlayer);
            playerCanvas.remove(gamePlayer);
            removePlayerState(gamePlayer);

            // Do not use minimum players here, there might be a lobby
            if (players.size() < 1) {
                stop();
            }
            getUserInterfaceHandler().destroy(gamePlayer);
            return true;
        }
        return false;
    }

    /**
     * Get players
     *
     * @return players
     */
    public final List<GamePlayer> getPlayers() {
        return players;
    }

    /**
     * Check if the player is playing
     *
     * @param player Player to check
     * @return is playing
     */
    public final boolean isPlaying(GamePlayer player) {
        return playerCanvas.containsKey(player);
    }

    /**
     * Game Unique identifier
     *
     * @return unique identifier
     */
    public final UUID getUUID() {
        return uuid;
    }

    /**
     * Set game Unique identifier
     *
     * @param uuid unique identifier
     */
    public final void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Get game ticks
     *
     * @return ticks
     */
    public final long getTicks() {
        return ticks;
    }

    /**
     * Set game ticks
     *
     * @param ticks ticks
     */
    public final void setTicks(long ticks) {
        this.ticks = ticks;
    }

    /**
     * Log debug message
     *
     * @param message message
     */
    public final void debug(String message) {
        if (userInterfaceHandler == null)
            return;
        userInterfaceHandler.debug(message);
    }

    /**
     * Log info message
     *
     * @param message message
     */
    public final void info(String message) {
        if (userInterfaceHandler == null)
            System.out.println("[" + getGameClass().getCanonicalName() + "] " + message);
        else
            userInterfaceHandler.info(message);
    }

    /**
     * Log warning message
     *
     * @param message message
     */
    public final void warning(String message) {
        if (userInterfaceHandler == null)
            System.out.println("[" + getGameClass().getCanonicalName() + "] " + message);
        else
            userInterfaceHandler.warning(message);
    }

    /**
     * Log severe message
     *
     * @param message message
     */
    public final void severe(String message) {
        if (userInterfaceHandler == null)
            System.out.println("[" + getGameClass().getCanonicalName() + "] " + message);
        else
            userInterfaceHandler.severe(message);
    }

    /**
     * Get update handler
     *
     * @return update handler
     */
    public final UserInterfaceHandler<T> getUserInterfaceHandler() {
        return userInterfaceHandler;
    }

    /**
     * Set update handler
     *
     * @param userInterfaceHandler Update handler
     */
    public final void setUserInterfaceHandler(UserInterfaceHandler<T> userInterfaceHandler) {
        this.userInterfaceHandler = userInterfaceHandler;
    }

    /**
     * Get game player by idx
     *
     * @param idx index
     * @return game player
     */
    public final GamePlayer getGamePlayer(int idx) {
        return players.get(idx);
    }

    /**
     * Get config file
     *
     * @return config file
     */
    public final File getConfigFile() {
        return configFile;
    }

    /**
     * Set config file
     *
     * @param configFile config file
     */
    public final void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    /**
     * Get data folder
     *
     * @return data folder
     */
    public final File getDataFolder() {
        return dataFolder;
    }

    /**
     * Set data folder
     *
     * @param dataFolder data folder
     */
    public final void setDataFolder(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    /**
     * Get configuration
     *
     * @return file configuration
     */
    public final FileConfiguration getConfig() {
        if (config == null) {
            this.reloadConfig();
        }
        return config;
    }

    /**
     * Set configuration
     *
     * @param config configuration
     */
    public final void setConfig(FileConfiguration config) {
        this.config = config;
    }

    /**
     * Save configuration
     *
     * @return success
     */
    public final void saveConfig() {
        try {
            getConfig().save(getConfigFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Reload configuration
     */
    public final void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defConfigStream = this.getClass().getResourceAsStream("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig;
            defConfig = new YamlConfiguration();

            byte[] contents;
            try {
                contents = ByteStreams.toByteArray(defConfigStream);
            } catch (IOException ex) {
                severe("Unexpected failure reading config.yml");
                return;
            }

            String text = new String(contents, Charset.defaultCharset());
            if (!text.equals(new String(contents, Charsets.UTF_8))) {
                warning("Default system encoding may have misread config.yml from plugin jar");
            }

            try {
                defConfig.loadFromString(text);
            } catch (InvalidConfigurationException ex) {
                severe("Cannot load configuration from jar");
            }
            this.config.setDefaults(defConfig);
        }
    }

    /**
     * Get arcadeboard plugin
     *
     * @return arcadeboard plugin
     */
    public final ArcadeBoardPlugin getPlugin() {
        return plugin;
    }

    public final void setPlugin(ArcadeBoardPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Get custom game settings
     *
     * @return custom game settings
     */
    public final Map<String, Object> getCustomGameSettings() {
        return this.customGameSettings;
    }

    /**
     * Add an option
     *
     * @param key   Game option to add
     * @param value value of option
     */
    public final void addCustomSetting(String key, Object value) {
        customGameSettings.put(key, value);
    }

    /**
     * Get option
     *
     * @param key game option
     * @return value
     */
    public final Object getCustomSetting(String key) {
        return customGameSettings.get(key);
    }


    /**
     * Get option
     *
     * @param key game option
     * @return value
     */
    public final String getCustomSettingString(String key) {
        return (String) getCustomSetting(key);
    }


    /**
     * Get option
     *
     * @param key game option
     * @return value
     */
    public final Integer getCustomSettingInt(String key) {
        if (!hasCustomSetting(key))
            return 0;
        return (Integer) getCustomSetting(key);
    }

    /**
     * Get option
     *
     * @param key game option
     * @return value
     */
    public final Boolean getCustomSettingBoolean(String key) {
        if (!hasCustomSetting(key))
            return false;
        return (Boolean) getCustomSetting(key);
    }

    /**
     * Check if the option is present
     *
     * @param key game option
     * @return has option
     */
    public final boolean hasCustomSetting(String key) {
        return customGameSettings.containsKey(key);
    }

    /**
     * Check if the game has a resource pack
     *
     * @return has resource pack
     */
    public final boolean hasResourcePack() {
        return hasOption(GameOption.RESOURCE_PACK);
    }

    /**
     * Reset the main canvas
     * <p>
     * Used when changing the dimensions of the canvas
     */
    public final void resetMainCanvas() {
        Canvas currentMain = getMainCanvas();
        setMainCanvas(createCanvas(getOptionInt(GameOption.SCREEN_WIDTH), getOptionInt(GameOption.SCREEN_HEIGHT)));
        for (final GamePlayer gamePlayer : getPlayers()) {
            if (playerCanvas.get(gamePlayer).equals(currentMain)) {
                playerCanvas.put(gamePlayer, getMainCanvas());
                Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {
                    public void run() {
                        getUserInterfaceHandler().destroy(gamePlayer);
                        getUserInterfaceHandler().construct(gamePlayer);
                    }
                });
            }
        }
    }

    /**
     * Reset the canvas of a specific player
     *
     * @param gamePlayer Player to reset canvas for
     * @param width      Width of the canvas
     * @param height     Height of the canvas
     */
    public final void resetCanvas(final GamePlayer gamePlayer, int width, int height) {
        playerCanvas.put(gamePlayer, createCanvas(width, height));
        Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {
            public void run() {
                getUserInterfaceHandler().destroy(gamePlayer);
                getUserInterfaceHandler().construct(gamePlayer);
            }
        });
    }

    /**
     * Reset the canvas of a specific player
     *
     * @param gamePlayer Player to reset canvas for
     */
    public final void resetCanvas(GamePlayer gamePlayer) {
        resetCanvas(gamePlayer, getOptionInt(GameOption.SCREEN_WIDTH), getOptionInt(GameOption.SCREEN_HEIGHT));
    }

    /**
     * Get current game state
     *
     * @return game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Set game state
     *
     * @param gameState game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        this.gameStateTicks = 0L;
    }

    /**
     * Get available game lobby
     *
     * @return game lobby
     */
    public GameLobby getAvailableLobby() {
        return getPlugin().getGameManager().getGameLobbyByGame(this);
    }

    /**
     * Create game lobby
     *
     * @return game lobby
     */
    public GameLobby createLobby() {
        return getPlugin().getGameManager().createGameLobby(this, null);
    }

    /**
     * Key press listener
     */
    public interface KeyDownListener {
        void onKeyDown(KeyDownEvent event);
    }

    /**
     * Key release listener
     */
    public interface KeyUpListener {
        void onKeyUp(KeyUpEvent event);
    }

    /**
     * Key listener covering keydown and keyup
     */
    public interface KeyListener extends KeyDownListener, KeyUpListener {

    }

    /**
     * Mouse listener covering mouse move and mouse click
     */
    public interface MouseListener extends MouseMoveListener, MouseClickListener {

    }


    /**
     * Mouse wheel listener
     */
    public interface MouseWheelListener {
        void onMouseWheel();
    }

    /**
     * Mouse move listener
     */
    public interface MouseMoveListener {
        void onMouseMove(MouseMoveEvent event);
    }

    /**
     * Mouse click listener
     */
    public interface MouseClickListener {
        void onMouseClick(MouseClickEvent event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Game game = (Game) o;

        return uuid != null ? uuid.equals(game.uuid) : game.uuid == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }
}
