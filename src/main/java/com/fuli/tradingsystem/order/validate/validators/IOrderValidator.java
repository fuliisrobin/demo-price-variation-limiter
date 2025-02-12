package com.fuli.tradingsystem.order.validate.validators;

import com.fuli.tradingsystem.entities.impl.Order;

public interface IOrderValidator {
	OrderValidateResult validate(Order o);
}
