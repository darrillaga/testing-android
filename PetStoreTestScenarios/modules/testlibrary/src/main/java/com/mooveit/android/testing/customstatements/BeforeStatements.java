package com.mooveit.android.testing.customstatements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BeforeStatements {
    boolean runAlways() default false;
    String[] runnersId() default {};
}
