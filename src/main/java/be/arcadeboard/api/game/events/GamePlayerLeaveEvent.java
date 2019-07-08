package be.arcadeboard.api.game.events;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.player.events.GamePlayerEvent;

public class GamePlayerLeaveEvent extends GamePlayerEvent {

    public GamePlayerLeaveEvent(Game game, GamePlayer gamePlayer, boolean async) {
        super(game, gamePlayer,async);
    }
}
