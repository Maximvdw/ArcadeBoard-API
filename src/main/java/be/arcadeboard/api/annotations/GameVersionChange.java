package be.arcadeboard.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GameVersionChange
 * <p>
 * Created by maxim on 15-Jan-17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GameVersionChange {
    /**
     * Version of change
     *
     * @return version
     */
    String version();

    /**
     * Module changelog
     *
     * @return value
     */
    String value();
}
