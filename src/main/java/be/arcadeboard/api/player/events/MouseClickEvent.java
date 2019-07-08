package be.arcadeboard.api.player.events;

import be.arcadeboard.api.player.GamePlayer;
import be.arcadeboard.api.game.Game;

/**
 * MouseClickEvent
 * Created by Maxim on 8/01/2018.
 */
public class MouseClickEvent extends MouseEvent {
    private MouseKey mouseKey = null;

    public MouseClickEvent(Game game, GamePlayer gamePlayer, MouseKey mouseKey, boolean async) {
        super(game, gamePlayer, async);
        setMouseKey(mouseKey);
    }

    /**
     * Get mouse key
     *
     * @return mouse key
     */
    public MouseKey getMouseKey() {
        return mouseKey;
    }

    /**
     * Set mouse key
     *
     * @param mouseKey Mouse key
     */
    public void setMouseKey(MouseKey mouseKey) {
        this.mouseKey = mouseKey;
    }

    public enum MouseKey {
        LEFT, RIGHT
    }
}
