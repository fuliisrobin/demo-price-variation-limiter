package com.fuli.tradingsystem.order.validate.service;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.validate.validators.OrderValidateResult;

/**
 * Interface of OrderValidationService
 */
public interface IOrderValidationService {
	public OrderValidateResult[] validate(Order order);
}