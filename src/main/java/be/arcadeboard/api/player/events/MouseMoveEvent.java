package be.arcadeboard.api.player.events;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.player.GamePlayer;

/**
 * Created by Maxim on 8/01/2018.
 */
public class MouseMoveEvent extends MouseEvent {
    private float deltaX = 0F;
    private float deltaY = 0F;

    public MouseMoveEvent(Game<?> game, GamePlayer gamePlayer, float deltaX, float deltaY, boolean async) {
        super(game, gamePlayer, async);
        setDeltaX(deltaX);
        setDeltaY(deltaY);
    }

    /**
     * Get X movement
     *
     * @return X movement
     */
    public float getDeltaX() {
        return deltaX;
    }

    /**
     * Set X Movement
     *
     * @param deltaX X Movement
     */
    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    /**
     * Get Y Movement
     *
     * @return Y movement
     */
    public float getDeltaY() {
        return deltaY;
    }

    /**
     * Set Y Movement
     *
     * @param deltaY Y movement
     */
    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }
}
