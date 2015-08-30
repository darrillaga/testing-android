package com.mooveit.android.testing.customstatements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodStatement {

    String DEFAULT_NAME = "";

    String value() default DEFAULT_NAME;

    String[] runnersId() default {};
}
