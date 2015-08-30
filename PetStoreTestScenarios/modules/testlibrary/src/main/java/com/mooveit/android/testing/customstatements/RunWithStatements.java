package com.mooveit.android.testing.customstatements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RunWithStatements {
    String[] before() default {};
    String[] after() default {};
    boolean runDefaults() default true;
}
