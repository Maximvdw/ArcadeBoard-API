package be.arcadeboard.api.player.events;


import be.arcadeboard.api.game.Game;
import be.arcadeboard.api.player.GamePlayer;

/**
 * KeyDownEvent
 * Created by Maxim on 8/01/2018.
 */
public class KeyEvent extends GamePlayerEvent {
    private Key key = null;

    public KeyEvent(Game game, GamePlayer gamePlayer, Key key, boolean async) {
        super(game, gamePlayer,async);
        setKey(key);
    }

    /**
     * Get pressed key
     *
     * @return pressed key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Set pressed key
     *
     * @param key pressed key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    public enum Key {
        // W A S D
        UP, DOWN, LEFT, RIGHT,
        // Space CTRL
        JUMP, SNEAK,
        // Numbers
        NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8, NUM_9,
        // Item drop
        DROP
    }
}
