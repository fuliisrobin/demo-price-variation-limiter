package com.fuli.tradingsystem.order.validate.validators;

public record OrderValidateResult(OrderValidationState level, String message)  {
}
