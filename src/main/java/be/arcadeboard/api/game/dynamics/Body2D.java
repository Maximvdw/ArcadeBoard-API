package be.arcadeboard.api.game.dynamics;

import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.game.geometry.Vector2D;

import java.awt.*;

public abstract class Body2D {
    private float x, y = 0;
    private Vector2D velocity = new Vector2D();
    private Game game = null;

    /**
     * Create a new body
     *
     * @param game Game
     */
    public Body2D(Game game) {
        setGame(game);
    }

    /**
     * Get X position
     *
     * @return X position
     */
    public float getX() {
        return x;
    }

    /**
     * Set X Position
     *
     * @param x X position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Get Y position
     *
     * @return Y position
     */
    public float getY() {
        return y;
    }

    /**
     * Set Y position
     *
     * @param y Y position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Update the body
     */
    public void update() {
        setX(x + getVelocity().getX());
        setY(y + getVelocity().getY());
    }

    /**
     * Get game
     *
     * @return game
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

    /**
     * Get body shape
     *
     * @return body shape
     */
    public abstract Shape getShape();

    /**
     * Get velocity
     *
     * @return velocity
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * Set velocity
     *
     * @param velocity Velocity
     */
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
}
