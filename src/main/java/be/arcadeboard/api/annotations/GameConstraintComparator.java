package be.arcadeboard.api.annotations;

import java.util.Comparator;

/**
 * GameConstraintComparator
 *
 * Created by maxim on 25-Jan-17.
 */
public class GameConstraintComparator implements Comparator<GameConstraint>{

    public int compare(GameConstraint o1, GameConstraint o2) {
        int priority1 = o1.type().getPriority();
        int priority2 = o2.type().getPriority();
        return priority1 > priority2 ? 1 : priority1 < priority2 ? -1 : 0;
    }
}
