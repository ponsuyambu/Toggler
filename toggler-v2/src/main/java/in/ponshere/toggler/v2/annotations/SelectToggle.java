package in.ponshere.toggler.v2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface SelectToggle {
    String key() default "";

    String displayName() default "";


    String defaultValue() default "";

    String[] selectOptions() default "";

}