package in.ponshere.toggler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface SelectToggle {
    String sharedPreferencesKey() default "";

    String defaultValue() default "";

    String fireBaseRemoteConfigKey() default "";

    String[] selectOptions() default "";

}