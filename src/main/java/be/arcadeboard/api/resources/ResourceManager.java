package be.arcadeboard.api.resources;

/**
 * Resource manager manages the resource pack(s) of ArcadeBoard and merging of games in one resource pack
 */
public interface ResourceManager {
    /**
     * Get default resource pack
     *
     * @return resource pack
     */
    ResourcePack getGlobalResourcePack();
}
