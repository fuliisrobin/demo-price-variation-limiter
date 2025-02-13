package com.fuli.tradingsystem.order.validate.aspects;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.place.service.OrderStatus;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.service.IOrderValidationService;
import com.fuli.tradingsystem.order.validate.validators.OrderValidationState;

@Aspect
@Component
public class OrderValidationAspect {
    private Logger logger = LoggerFactory.getLogger(OrderValidationAspect.class);

    public OrderValidationAspect() {
	logger.info("Order validation aspect loaded.");
    }

    @Autowired
    private IOrderValidationService validationService;

    @Around("@annotation(com.fuli.tradingsystem.order.validate.annotations.ValidateOrder)")
    public Object validateOrder(ProceedingJoinPoint joinPoint) throws Throwable {
	Object[] args = joinPoint.getArgs();
	if (args.length > 0 && args[0] != null && Order.class.isAssignableFrom(args[0].getClass())) {
	    Order order = (Order) args[0];
	    boolean hasBlocker = Arrays.stream(this.validationService.validate(order))
		    .anyMatch(result -> (result.level().getLevel() >= OrderValidationState.Block.getLevel()));
	    if (hasBlocker) {
		PlaceOrderResult result = new PlaceOrderResult();
		result.setStatus(OrderStatus.Blocked);
		result.setValidationResults(this.validationService.validate(order));
		result.setMessage("Failed to place order, validation failed");
		return result;
	    } else {
		try {
		    PlaceOrderResult result = (PlaceOrderResult) joinPoint.proceed();
		    result.setValidationResults(this.validationService.validate(order));
		    return result;
		} catch (Throwable e) {
		    throw new RuntimeException("Error proceeding place order", e);
		}
	    }
	} else {
	    logger.warn("Order validation bypassed because of bad method signature.");
	    return joinPoint.proceed();
	}
    }

}