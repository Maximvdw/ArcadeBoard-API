package be.arcadeboard.api.player.events;


import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.player.GamePlayer;

/**
 * KeyDownEvent
 * Created by Maxim on 8/01/2018.
 */
public class KeyDownEvent extends KeyEvent {

    public KeyDownEvent(Game game, GamePlayer gamePlayer, Key key, boolean async) {
        super(game, gamePlayer,key, async);
    }
}
