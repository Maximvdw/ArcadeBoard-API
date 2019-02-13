package be.arcadeboard.api.game;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * GameInformation
 * <p>
 * Contains required information prior to running a game
 */
public class GameInformation implements Serializable {
    private String name = "";
    private String displayName = "";
    private String description = "";
    private String controls = "";
    private String author = "";
    private String version = "";
    private Class<? extends Game> gameClass = null;
    private File jarFile = null;
    private Map<GameOption, Object> gameOptions = new HashMap<GameOption, Object>();

    public GameInformation(Class<? extends Game> gameClass) {
        setGameClass(gameClass);
    }

    public GameInformation() {

    }

    /**
     * Get game name
     *
     * @return game name
     */
    public final String getName() {
        return name;
    }

    /**
     * Set game name
     *
     * @param name game name
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * Get game display name
     *
     * @return game display name
     */
    public final String getDisplayName() {
        return displayName;
    }

    /**
     * Set game display name
     *
     * @param displayName game display name
     */
    public final void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get game description
     *
     * @return description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Set game description
     *
     * @param description Game description
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get game controls
     *
     * @return game controls
     */
    public final String getControls() {
        return controls;
    }

    /**
     * Set game controls
     *
     * @param controls game controls
     */
    public final void setControls(String controls) {
        this.controls = controls;
    }

    /**
     * Get game author
     *
     * @return game author
     */
    public final String getAuthor() {
        return author;
    }

    /**
     * Set game author
     *
     * @param author game author
     */
    public final void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get game version
     *
     * @return game version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * Set game version
     *
     * @param version version
     */
    public final void setVersion(String version) {
        this.version = version;
    }

    /**
     * Check if player has permission for the game
     *
     * @param player Player
     * @return permission to play
     */
    public boolean hasPermission(Player player) {
        return player.hasPermission("arcadeboard.games.play.*") || player.hasPermission("arcadeboard.games.play." + getName().toLowerCase());
    }

    /**
     * Get game JAR file
     *
     * @return game JAR file
     */
    public final File getJarFile() {
        return jarFile;
    }

    /**
     * Set game JAR file
     *
     * @param jarFile game JAR file
     */
    public final void setJarFile(File jarFile) {
        this.jarFile = jarFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInformation that = (GameInformation) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    public Map<GameOption, Object> getGameOptions() {
        return gameOptions;
    }

    public void setGameOptions(Map<GameOption, Object> gameOptions) {
        this.gameOptions = gameOptions;
    }

    public Class<? extends Game> getGameClass() {
        return gameClass;
    }

    public void setGameClass(Class<? extends Game> gameClass) {
        this.gameClass = gameClass;
    }
}
