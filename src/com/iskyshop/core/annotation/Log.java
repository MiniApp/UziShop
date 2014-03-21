package com.iskyshop.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.iskyshop.foundation.domain.LogType;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {
    public abstract String title();

    public abstract String entityName() default "";

    public abstract LogType type();

    public abstract String description() default "";

    public abstract String ip() default "";
}