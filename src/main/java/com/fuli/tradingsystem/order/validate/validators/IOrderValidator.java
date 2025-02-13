package com.fuli.tradingsystem.order.validate.validators;

import com.fuli.tradingsystem.entities.impl.Order;

/**
 * Interface to validate the orders, its instances are inteded to be injected to
 * OrderValidatorService as @Autowired.
 */
public interface IOrderValidator {
    /**
     * Validate the order with this validator.
     * 
     * @param order Order object to be validated
     * @return OrderValidateResult
     */
    OrderValidateResult validate(Order order);
}
