package be.arcadeboard.api.player.events;


import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.player.GamePlayer;

/**
 * Created by Maxim on 8/01/2018.
 */
public class MouseEvent extends GamePlayerEvent{
    public MouseEvent(Game game, GamePlayer gamePlayer) {
        super(game, gamePlayer,false);
    }
}
