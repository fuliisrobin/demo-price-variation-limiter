package com.fuli.tradingsystem.order.validate.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fuli.tradingsystem.entities.InstrumentType;
import com.fuli.tradingsystem.entities.TradeSide;
import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.entities.impl.OrderUtils;

public class BasicOrderInfoValidatorTest {

    private BasicOrderInfoValidator validator;
    private Order order;

    @BeforeEach
    void setUp() {
	validator = new BasicOrderInfoValidator();

	order = OrderUtils.createOrder(InstrumentType.Option, "KS200400F5.KS", TradeSide.Buy, 8.81, 10);
    }

    @Test
    void testValidOrder() {
	OrderValidateResult result = validator.validate(order);
	assertEquals(OrderValidationState.Pass, result.level());
	assertEquals("Basic info validation passed.", result.message());
    }

    @Test
    void testNullInstrument() {
	order.setInstrument(null);
	OrderValidateResult result = validator.validate(order);
	assertEquals(OrderValidationState.Block, result.level());
	assertEquals("Invalid instrument.", result.message());
    }

    @Test
    void testNullPrice() {
	order.setPrice(null);
	OrderValidateResult result = validator.validate(order);
	assertEquals(OrderValidationState.Block, result.level());
	assertEquals("Price is not specified.", result.message());
    }

    @Test
    void testNullSide() {
	order.setSide(null);
	OrderValidateResult result = validator.validate(order);
	assertEquals(OrderValidationState.Block, result.level());
	assertEquals("Side is not specified.", result.message());
    }
}
