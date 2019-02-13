package be.arcadeboard.api;

import be.arcadeboard.api.resources.ResourceManager;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * ArcadeBoardPlugin
 *
 * @code ArcadeBoardPlugin plugin = (ArcadeBoardPlugin) Bukkit.getPluginManager().getPlugin("ArcadeBoard");
 * <p>
 * Created by Maxim on 16/01/2018.
 */
public interface ArcadeBoardPlugin extends Plugin {
    /**
     * Get games directory
     *
     * @return games directory
     */
    File getGamesDirectory();

    /**
     * Get game manager
     *
     * @return
     */
    GameManager getGameManager();

    /**
     * Get resource manager
     *
     * @return resource manager
     */
    ResourceManager getResourceManager();

    /**
     * Get API version
     * Check https://www.spigotmc.org/wiki/arcadeboard-api-changelog
     *
     * @return API version
     */
    int getAPIVersion();
}
