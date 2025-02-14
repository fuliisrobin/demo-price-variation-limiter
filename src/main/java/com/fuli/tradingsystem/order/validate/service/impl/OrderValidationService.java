package com.fuli.tradingsystem.order.validate.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.validate.service.IOrderValidationService;
import com.fuli.tradingsystem.order.validate.validators.IOrderValidator;
import com.fuli.tradingsystem.order.validate.validators.OrderValidateResult;
import com.fuli.tradingsystem.order.validate.validators.OrderValidationState;

@Component
public class OrderValidationService implements IOrderValidationService {
    // These validators are injected by spring, there can be multiple validators
    @Autowired(required = false)
    private List<IOrderValidator> validateChain = new LinkedList<>();

    /**
     * Validate and return the array of result. If prior one validation fails, the
     * remaining validators will not execute.
     */
    public OrderValidateResult[] validate(Order order) {

	OrderValidateResult[] results = new OrderValidateResult[validateChain.size()];
	int resultCount = 0;
	for (IOrderValidator validator : validateChain) {
	    OrderValidateResult result = results[resultCount++] = validator.validate(order);
	    if (result.level().getLevel() >= OrderValidationState.Block.getLevel()) {
		break;
	    }
	}
	return resultCount == results.length ? results : Arrays.copyOf(results, resultCount);

    }

    /**
     * Mostly for UT
     * 
     * @param validators
     */
    void setValidators(List<IOrderValidator> validators) {
	this.validateChain = validators;

    }
}