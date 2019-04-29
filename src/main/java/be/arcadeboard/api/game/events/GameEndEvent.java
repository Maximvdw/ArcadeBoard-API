package be.arcadeboard.api.game.events;

import be.arcadeboard.api.game.Game;
import org.bukkit.event.Cancellable;

public class GameEndEvent extends GameEvent implements Cancellable{
    public GameEndEvent(Game game) {
        super(game,true);
    }

    public boolean isCancelled() {
        return false;
    }

    public void setCancelled(boolean b) {

    }
}
