package be.arcadeboard.api.game;

import be.arcadeboard.api.resources.ResourceIcon;
import be.arcadeboard.api.resources.ResourcePack;

public enum GameOption {
    /**
     * Enable or disable the game
     */
    ENABLED(Boolean.class),
    /**
     * Aimed TPS
     */
    TPS(Integer.class),
    /**
     * Use a global canvas for all players
     */
    GLOBAL_CANVAS(Boolean.class),
    /**
     * Enable or disable scrolling
     */
    SCROLL_ENABLED(Boolean.class),
    /**
     * Set a required resource pack
     */
    RESOURCE_PACK(ResourcePack.class),
    /**
     * Screen width
     */
    SCREEN_WIDTH(Integer.class),
    /**
     * Screen height
     */
    SCREEN_HEIGHT(Integer.class),
    /**
     * Minimum amount of players to start the game
     */
    MINIMUM_PLAYERS(Integer.class),
    /**
     * Maximum amount of players to start the game
     */
    MAXIMUM_PLAYERS(Integer.class),
    /**
     * The initial delay before repeating a KeyDown
     */
    KEY_REPEAT_DELAY(Integer.class),
    /**
     * The interval rate to fire the KeyDown after the initial delay
     */
    KEY_REPEAT_RATE(Integer.class),
    /**
     * The initial delay before repeating a MouseClick
     */
    MOUSE_REPEAT_DELAY(Integer.class),
    /**
     * The interval rate to fire the MouseClick after the initial delay
     */
    MOUSE_REPEAT_RATE(Integer.class),
    /**
     * The version of the game config
     */
    CONFIG_VERSION(Integer.class),
    /**
     * Icon used for the game
     */
    CHEST_GAME_ICON(String.class),
    /**
     * Icon used for the game
     */
    GUI_GAME_ICON(ResourceIcon.class),
    /**
     * Enable black background
     */
    BACKGROUND(Choice.class);

    private Class<?> type = Object.class;

    GameOption(Class<?> type){
        setType(type);
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * Game option choice
     */
    public enum Choice{
        /**
         * Option is disabled
         */
        DISABLED,
        /**
         * Option is enabled
         */
        ENABLED,
        /**
         * Option is optional, but enabled by default
         */
        OPT_OUT,
        /**
         * Option optional, but disabled by default
         */
        OPT_IN,
    }
}
