package be.arcadeboard.api;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.GameInformation;
import be.arcadeboard.api.game.GameLobby;
import be.arcadeboard.api.game.statistics.Statistic;
import be.arcadeboard.api.game.statistics.StatisticInterval;
import be.arcadeboard.api.game.statistics.StatisticType;
import be.arcadeboard.api.player.GamePlayer;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface GameManager {
    /**
     * Get running games
     *
     * @return games
     */
    List<Game> getRunningGames();

    /**
     * Get online game players
     *
     * @return game players
     */
    Collection<GamePlayer> getOnlinePlayers();

    /**
     * Add a running game
     *
     * @param game Game to add
     */
    void addRunningGame(Game game);

    /**
     * Get running game by UUID
     *
     * @param uuid UUID of the game
     * @return Game if found
     */
    Game getRunningGameByUUID(UUID uuid);

    /**
     * Get game lobby by uuid
     *
     * @param uuid game UUID
     * @return game lobby
     */
    GameLobby getGameLobbyByUUID(UUID uuid);

    /**
     * Get game lobby by the game information
     *
     * @param gameInformation Game
     * @return game lobby
     */
    GameLobby getGameLobbyByGame(GameInformation gameInformation);

    /**
     * Get running game by player
     *
     * @param player game player
     * @return running game if found
     */
    Game getRunningGameByPlayer(GamePlayer player);

    /**
     * Remove a running game
     *
     * @param game Game to remove
     * @return Success
     */
    boolean removeRunningGame(Game game);

    /**
     * Check if the player is in a game
     *
     * @param player Player
     * @return in game
     */
    boolean isPlaying(GamePlayer player);

    /**
     * Add an online player
     *
     * @param gamePlayer Game player
     */
    void addPlayer(GamePlayer gamePlayer);

    /**
     * Get game player profile of offline player
     *
     * @param player Player
     * @return game player
     */
    GamePlayer getGamePlayer(OfflinePlayer player);

    /**
     * Get game by name
     *
     * @param shortName name of the game
     * @return Game informaton if found
     */
    GameInformation getGameByName(String shortName);

    /**
     * Remove a player
     *
     * @param gamePlayer Game player to remove
     * @return success
     */
    boolean removePlayer(GamePlayer gamePlayer);

    /**
     * Get available games
     *
     * @return available games
     */
    List<GameInformation> getAvailableGames();

    /**
     * Get game lobbies
     *
     * @return game lobbies
     */
    List<GameLobby> getGameLobbies();

    /**
     * Create a new game lobby
     *
     * @param game   Running game
     * @param player Player
     * @return lobby
     */
    GameLobby createGameLobby(Game game, GamePlayer player);

    /**
     * Create a new game
     *
     * @param game game information
     * @return Game instance
     */
    Game createGame(GameInformation game);

    /**
     * Play a game
     *
     * @param gamePlayer      Player that wants to play
     * @param gameInformation Game that the player wants to play
     */
    void playGame(GamePlayer gamePlayer, GameInformation gameInformation);

    /**
     * Get top statistics
     *
     * @param game     Game to get top statistics of
     * @param type     Type of statistic
     * @param interval Interval of top
     * @param amount   Amount
     * @return
     */
    List<Statistic> getTopStatistics(Game game, StatisticType type, StatisticInterval interval, int amount);
}