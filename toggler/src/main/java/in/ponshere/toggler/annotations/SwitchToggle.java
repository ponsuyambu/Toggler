package in.ponshere.toggler.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface SwitchToggle {
    String sharedPreferencesKey() default "";

    boolean defaultValue() default true;

    String fireBaseRemoteConfigKey() default "";
}
