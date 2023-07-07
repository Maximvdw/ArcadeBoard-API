package be.arcadeboard.api.game.statistics;

/**
 * Statistic types
 */
public enum StatisticType {
    /**
     * Amount of victories (incremental)
     *
     * Can be used in placeholders:
     * PlaceholderAPI:      %arcadeboard_XXXX_statistic_victories%
     * With XXXX being the game name configured with the @GameName annotation
     */
    VICTORIES,
    /**
     * Personal best score
     *
     * Can be used in placeholders:
     * PlaceholderAPI:      %arcadeboard_XXXX_statistic_best_score%
     * With XXXX being the game name configured with the @GameName annotation
     */
    BEST_SCORE,
    /**
     * Amount of defeats (incremental)
     *
     * Can be used in placeholders:
     * PlaceholderAPI:      %arcadeboard_XXXX_statistic_defeats%
     * With XXXX being the game name configured with the @GameName annotation
     */
    DEFEATS,
    /**
     * Maximum level
     *
     * Can be used in placeholders:
     * PlaceholderAPI:      %arcadeboard_XXXX_statistic_level%
     * With XXXX being the game name configured with the @GameName annotation
     */
    LEVEL
}
