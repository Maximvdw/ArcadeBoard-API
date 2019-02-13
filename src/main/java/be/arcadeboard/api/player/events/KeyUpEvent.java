package be.arcadeboard.api.player.events;


import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.game.Game;

/**
 * KeyDownEvent
 * Created by Maxim on 8/01/2018.
 */
public class KeyUpEvent extends KeyEvent {
    public KeyUpEvent(Game game, GamePlayer gamePlayer, KeyEvent.Key key) {
        super(game, gamePlayer,key);
    }
}
