package be.arcadeboard.api.implementation;

import be.arcadeboard.api.game.graphics.Canvas;
import be.arcadeboard.api.player.GamePlayer;

import java.util.Map;

public interface UserInterfaceHandler<T extends Canvas> {
    /**
     * Fired when the game is started
     * individually for each player
     *
     * @param player Game player
     */
    void construct(GamePlayer player);

    /**
     * Fired after each loop
     *
     * @param canvasMap Canvas of each game player
     */
    void update(Map<GamePlayer, T> canvasMap);

    /**
     * Fired when the game is stopped
     * individually for each player
     *
     * @param player GAme player
     */
    void destroy(GamePlayer player);

    void error(Exception ex);

    void severe(String message);

    void info(String info);

    void warning(String warning);

    void debug(String message);
}
