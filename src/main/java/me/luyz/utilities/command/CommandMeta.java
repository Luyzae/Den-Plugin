package me.luyz.utilities.command;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMeta {
    String name() default "";
    String permission() default "";
    String[] aliases() default {};
    String description() default "";
    String usage() default "";
    boolean inGameOnly() default true;
}