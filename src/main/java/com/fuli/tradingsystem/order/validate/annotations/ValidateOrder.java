package com.fuli.tradingsystem.order.validate.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to decorate the target method so that the aspectj could creat pointcut around it.
 * It's intended to pointcut the placeOrderService.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ValidateOrder {
}
