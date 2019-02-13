package be.arcadeboard.api.game.events;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.player.events.GamePlayerEvent;

public class GamePlayerJoinEvent extends GamePlayerEvent {

    public GamePlayerJoinEvent(Game game, GamePlayer gamePlayer) {
        super(game, gamePlayer);
    }
}
