package com.fuli.tradingsystem.order.validate.aspects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fuli.tradingsystem.entities.InstrumentType;
import com.fuli.tradingsystem.entities.TradeSide;
import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.entities.impl.OrderUtils;
import com.fuli.tradingsystem.order.place.service.OrderStatus;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.service.IOrderValidationService;
import com.fuli.tradingsystem.order.validate.validators.OrderValidateResult;
import com.fuli.tradingsystem.order.validate.validators.OrderValidationState;

public class OrderValidationAspectTest {

    @Mock
    private IOrderValidationService validationService;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private OrderValidationAspect orderValidationAspect;
    private Order order;

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);
	order = OrderUtils.createOrder(InstrumentType.Option, "KS200400F5.KS", TradeSide.Buy, 8.81, 10);
    }

    @Test
    public void testValidateOrderWithBlockingValidation() throws Throwable {
	OrderValidateResult blockingResult = new OrderValidateResult(OrderValidationState.Block, "Blocking validation");
	OrderValidateResult[] results = new OrderValidateResult[] { blockingResult };

	when(joinPoint.getArgs()).thenReturn(new Object[] { order });
	when(validationService.validate(order)).thenReturn(results);

	PlaceOrderResult result = (PlaceOrderResult) orderValidationAspect.validateOrder(joinPoint);

	assertEquals(OrderStatus.Blocked, result.getStatus());
	assertEquals(results, result.getValidationResults());
	assertEquals("Failed to place order, validation failed", result.getMessage());
    }

    @Test
    public void testValidateOrderWithNonBlockingValidation() throws Throwable {
	OrderValidateResult nonBlockingResult = new OrderValidateResult(OrderValidationState.Pass,
		"Non-blocking validation");
	OrderValidateResult[] results = new OrderValidateResult[] { nonBlockingResult };
	PlaceOrderResult expectedResult = new PlaceOrderResult();

	when(joinPoint.getArgs()).thenReturn(new Object[] { order });
	when(validationService.validate(order)).thenReturn(results);
	when(joinPoint.proceed()).thenReturn(expectedResult);

	PlaceOrderResult result = (PlaceOrderResult) orderValidationAspect.validateOrder(joinPoint);

	assertEquals(expectedResult, result);
	assertEquals(results, result.getValidationResults());
    }

}