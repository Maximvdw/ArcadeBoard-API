package be.arcadeboard.api.game;

import be.arcadeboard.api.player.GamePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GameLobby
 * Created by Maxim on 7/01/2018.
 */
public class GameLobby {
    private Game<?> game = null;
    private List<GamePlayer> players = new ArrayList<GamePlayer>();
    private long creationTime = System.currentTimeMillis();
    private Map<GamePlayer, Boolean> readyState = new HashMap<GamePlayer, Boolean>();

    public GameLobby(Game game) {
        setGame(game);
    }

    /**
     * Get the game
     *
     * @return Game
     */
    public Game<?> getGame() {
        return game;
    }

    /**
     * Set the game
     *
     * @param game Game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Get players
     *
     * @return players
     */
    public List<GamePlayer> getPlayers() {
        return players;
    }

    /**
     * Set players
     *
     * @param players Players
     */
    public void setPlayers(List<GamePlayer> players) {
        this.players = players;
    }

    /**
     * Get creation time
     *
     * @return creation time
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * Set creation time
     *
     * @param creationTime Creation time
     */
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Check if the player is ready
     *
     * @param gamePlayer game player
     * @return ready or not
     */
    public boolean isReady(GamePlayer gamePlayer) {
        return readyState.get(gamePlayer);
    }

    /**
     * Check if lobby is full
     *
     * @return lobby full
     */
    public boolean isFull() {
        return players.size() >= getGame().getOptionInt(GameOption.MAXIMUM_PLAYERS);
    }

    /**
     * Set if the player is ready
     *
     * @param gamePlayer game player
     * @param ready      ready or not
     */
    public void setReadyState(GamePlayer gamePlayer, boolean ready) {
        readyState.put(gamePlayer, ready);
    }

    /**
     * Remove a player
     *
     * @param player Player to remove
     */
    public void removePlayer(GamePlayer player) {
        players.remove(player);
        readyState.remove(player);
    }

    /**
     * Add a player
     *
     * @param player Player to add
     */
    public void addPlayer(GamePlayer player) {
        if (player == null)
            return;
        players.add(player);
        readyState.put(player, false);
    }
}
