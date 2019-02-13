package be.arcadeboard.api.game.events;

import be.arcadeboard.api.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Game game = null;
    private boolean cancelled = false;

    public GameEvent(Game game){
        setGame(game);
    }

    /**
     * Get game
     *
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set game
     *
     * @param game Game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
