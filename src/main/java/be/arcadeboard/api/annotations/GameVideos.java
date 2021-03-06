package be.arcadeboard.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GameVideos
 * <p>
 * Created by maxim on 16-Jan-17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GameVideos {
    /**
     * Videos links
     *
     * @return videos links
     */
    String[] value();
}
