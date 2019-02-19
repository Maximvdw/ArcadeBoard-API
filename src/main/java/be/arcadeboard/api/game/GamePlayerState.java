package be.arcadeboard.api.game;

import be.arcadeboard.api.player.GamePlayer;

/**
 * GameState
 * Created by Maxim on 23/01/2018.
 */
public interface GamePlayerState {
    /**
     * Handle state
     *
     * @param gamePlayer Game player
     */
    void handle(GamePlayer gamePlayer);
}
