package com.fuli.tradingsystem.order.validate.validators;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.Order;

/**
 * Validator which verify the common required fields of an order.
 */
@Component
@org.springframework.core.annotation.Order(1)
public class BasicOrderInfoValidator implements IOrderValidator {

	/**
	 * Validate the common required fields of an order.
	 */
	@Override
	public OrderValidateResult validate(Order order) {
		if (order.getInstrument() == null || order.getInstrument().getSymbol() == null
				|| order.getInstrument().getSymbol() == null || order.getInstrument().getType() == null) {
			return new OrderValidateResult(OrderValidationState.Block, "Invalid instrument.");
		} else if (order.getPrice() == null) {
			return new OrderValidateResult(OrderValidationState.Block, "Price is not specified.");
		} else if (order.getSide() == null) {
			return new OrderValidateResult(OrderValidationState.Block, "Side is not specified.");
		}
		return new OrderValidateResult(OrderValidationState.Pass, "Basic info validation passed.");
	}

}
