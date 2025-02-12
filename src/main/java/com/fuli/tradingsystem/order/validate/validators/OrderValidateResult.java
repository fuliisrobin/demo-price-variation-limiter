package com.fuli.tradingsystem.order.validate.validators;

/**
 * Record for order validation, could result order validation Pass or Block
 * Skip, Pass, Block
 */
public record OrderValidateResult(OrderValidationState level, String message)  {
}
