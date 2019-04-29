package be.arcadeboard.api.player.events;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.game.events.GameEvent;

public class GamePlayerEvent extends GameEvent{
    private GamePlayer gamePlayer = null;

    public GamePlayerEvent(Game game, GamePlayer gamePlayer, boolean async) {
        super(game,async);
        setGamePlayer(gamePlayer);
    }

    /**
     * Get game player
     *
     * @return Game player
     */
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    /**
     * Set game player
     *
     * @param gamePlayer Game player
     */
    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}
