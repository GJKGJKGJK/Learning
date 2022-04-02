package com.gjk.demo_lean.spring.aop.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GlobalErrorCatch
 *
 * @author: GJK
 * @date: 2022/3/31 15:11
 * @description:
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalErrorCatch {

    String methodName() default "";
}
