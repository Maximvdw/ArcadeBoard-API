package be.arcadeboard.api.game.events;

import be.arcadeboard.api.game.Game;
import org.bukkit.event.Cancellable;

public class GameStartEvent extends GameEvent implements Cancellable{

    public GameStartEvent(Game game, boolean async) {
        super(game,async);
    }

    public boolean isCancelled() {
        return false;
    }

    public void setCancelled(boolean b) {

    }
}
