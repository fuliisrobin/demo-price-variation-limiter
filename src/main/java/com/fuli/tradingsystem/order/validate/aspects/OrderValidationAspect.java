package com.fuli.tradingsystem.order.validate.aspects;


import java.util.Arrays;
import java.util.function.Supplier;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.place.IPlaceOrderOption;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;
import com.fuli.tradingsystem.order.place.service.OrderStatus;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.service.IOrderValidationService;
import com.fuli.tradingsystem.order.validate.validators.OrderValidationState;
import com.fuli.tradingsystem.order.validate.validators.OrderValidateResult;

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
        if(args.length > 0 && (args[0].getClass().isAssignableFrom(PlaceOrderAction.class))) {
        	PlaceOrderAction<Order, IPlaceOrderOption> action = (PlaceOrderAction<Order, IPlaceOrderOption>) args[0];
        	IPlaceOrderOption options = action.getOptions();
        	Order order = action.getOrder();
        	return validateAndProceed(options, order, ()->{
				try {
					return (PlaceOrderResult)joinPoint.proceed();
				} catch (Throwable e) {
					throw new RuntimeException("Error proceeding place order", e);
				}
			});
        } else {
        	return joinPoint.proceed();
        }
    }

	PlaceOrderResult validateAndProceed(IPlaceOrderOption options, Order order, Supplier<PlaceOrderResult> suppiler)
		 {
		OrderValidateResult[] results = this.validationService.validate(order);
		boolean hasBlocker = Arrays.stream(results).anyMatch(result->(result.level().getLevel() >= OrderValidationState.Block.getLevel()));
		if(hasBlocker) {
			PlaceOrderResult result = new PlaceOrderResult();
			result.setStatus(OrderStatus.Blocked);
			result.setValidationResults(results);
			result.setMessage("Failed to place order, validation failed");
			return result;
		} else {
			PlaceOrderResult result = (PlaceOrderResult) suppiler.get();
			result.setValidationResults(results);
			return result;
		}
	}
}