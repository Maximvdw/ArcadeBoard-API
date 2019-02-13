package be.arcadeboard.api.game.statistics;

import be.arcadeboard.api.player.GamePlayer;

public class Statistic {
    private GamePlayer gamePlayer = null;
    private StatisticType type = StatisticType.BEST_SCORE;
    private double value = 0;
    private long updateTime = 0;

    /**
     * Get statistic type
     *
     * @return type
     */
    public StatisticType getType() {
        return type;
    }

    /**
     * Set statistic type
     *
     * @param type type
     */
    public void setType(StatisticType type) {
        this.type = type;
    }

    /**
     * Get value
     *
     * @return value
     */
    public double getValue() {
        return value;
    }

    /**
     * Set value
     *
     * @param value Value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Get game player
     *
     * @return game player
     */
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    /**
     * Set game player
     *
     * @param gamePlayer Game player
     */
    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    /**
     * Get the update time of the statistic
     *
     * @return update time
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Set the update time of the statistic
     *
     * @param updateTime update time
     */
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
