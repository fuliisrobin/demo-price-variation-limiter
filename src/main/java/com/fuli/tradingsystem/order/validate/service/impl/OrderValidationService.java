package com.fuli.tradingsystem.order.validate.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.validate.service.IOrderValidationService;
import com.fuli.tradingsystem.order.validate.validators.IOrderValidator;
import com.fuli.tradingsystem.order.validate.validators.OrderValidateResult;

@Component
public class OrderValidationService implements IOrderValidationService {
	// These validators are injected by spring, there can be multiple validators
	@Autowired(required = false)
	private List<IOrderValidator> validators = new ArrayList<>();
	
	/**
	 * Validate and return the array of result.
	 */
	public OrderValidateResult[] validate(Order order) {
		return validators.stream().map(validator -> validator.validate(order)).toArray(OrderValidateResult[]::new);
	}
}