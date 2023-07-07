package be.arcadeboard.api.player;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.statistics.Statistic;
import be.arcadeboard.api.game.GameInformation;
import be.arcadeboard.api.game.statistics.StatisticType;
import be.arcadeboard.api.music.NoteBlockSong;
import be.arcadeboard.api.music.Sound;
import be.arcadeboard.api.resources.ResourceSound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public interface GamePlayer extends Serializable {
    /**
     * Get player name
     *
     * @return player name
     */
    String getName();

    /**
     * Get player unique identifier
     *
     * @return player unique identifier
     */
    UUID getUniqueIdentifier();

    /**
     * Get bukkit player
     *
     * @return bukkit player
     */
    Player getPlayer();

    /**
     * Play a sound for the player
     *
     * @param sound  Sound to play
     * @param volume Volume of the sound
     * @param pitch  Pitch of the sound
     */
    void playSound(Sound sound, int volume, int pitch);

    /**
     * Play a sound for the player
     *
     * @param sound  Sound to play
     * @param volume Volume of the sound
     * @param pitch  Pitch of the sound
     */
    @Deprecated
    void playSound(org.bukkit.Sound sound, int volume, int pitch);

    /**
     * Play a sound for the player
     *
     * @param sound    Sound to play
     * @param category Category to play on
     * @param volume   Volume of the sound
     * @param pitch    Pitch of the sound
     */
    void playSound(String sound, SoundCategory category, int volume, int pitch);

    /**
     * Play a sound for the player
     *
     * @param resourceSound Resource sound to play
     * @param volume        Volume of the sound
     * @param pitch         Pitch of the sound
     */
    void playSound(ResourceSound resourceSound, int volume, int pitch);


    /**
     * Play a sound for the player
     *
     * @param resourceSound Resource sound to play
     */
    void playSound(ResourceSound resourceSound);


    /**
     * Get the song that is currently playing for the player
     *
     * @return ResourceSong that is playing
     */
    NoteBlockSong getPlayingSong();

    /**
     * Play a song for the player async
     *
     * @param song ResourceSong to play
     */
    void playSong(NoteBlockSong song);

    /**
     * Check if a song is playing for the player
     *
     * @return is a song playing
     */
    boolean hasSongPlaying();

    /**
     * Check if a player has a noteblock song playing
     *
     * @param song noteblock song
     * @return has playing
     */
    boolean hasSongPlaying(NoteBlockSong song);

    /**
     * Stop playing a specific noteblock song
     *
     * @param song noteblock song
     */
    void stopSong(NoteBlockSong song);

    /**
     * Get game statistic for player
     *
     * @param game game to get statistic for
     * @param type statistic type
     * @return statistic if found
     */
    Statistic getStatistic(GameInformation game, StatisticType type);

    /**
     * Store a game statistic
     *
     * @param game  game to get statistic for
     * @param type  statistic type
     * @param value statistic value
     * @return
     */
    Statistic storeStatistic(GameInformation game, StatisticType type, double value);

    /**
     * Get persistent game data
     *
     * @param game Game
     * @param key  Key
     * @return data
     */
    String getGameData(GameInformation game, String key);

    /**
     * Get persistent game data
     *
     * @param game Game
     * @param key  Key
     * @param value Default value
     * @return data
     */
    String getGameData(GameInformation game, String key, Object defaultValue);

    /**
     * Store persistent game data
     *
     * @param game  Game
     * @param key   Key
     * @param value data
     */
    void storeGameData(GameInformation game, String key, String value);

    /**
     * Get persistent player settings
     *
     * @param key Key
     * @return data
     */
    String getGlobalSetting(String key);

    /**
     * Get persistent player settings
     *
     * @param key Key
     * @param defaultValue Default value
     * @return data
     */
    String getGlobalSetting(String key, Object defaultValue);

    /**
     * Store persistent setting data
     *
     * @param key   Key
     * @param value data
     */
    void storeGlobalSetting(String key, String value);

    /**
     * Invite the player to a game
     *
     * @param game Running game
     * @return invitation successful
     */
    boolean invite(Game game);
}
