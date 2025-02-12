package com.fuli.tradingsystem.order.validate.aspects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

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
import com.fuli.tradingsystem.order.place.IPlaceOrderOption;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;
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
		IPlaceOrderOption options = mock(IPlaceOrderOption.class);
		PlaceOrderAction<Order, IPlaceOrderOption> action = new PlaceOrderAction<>(order, options);
		OrderValidateResult blockingResult = new OrderValidateResult(OrderValidationState.Block, "Blocking validation");
		OrderValidateResult[] results = new OrderValidateResult[] { blockingResult };

		when(joinPoint.getArgs()).thenReturn(new Object[] { action });
		when(validationService.validate(order)).thenReturn(results);

		PlaceOrderResult result = (PlaceOrderResult) orderValidationAspect.validateOrder(joinPoint);

		assertEquals(OrderStatus.Blocked, result.getStatus());
		assertEquals(results, result.getValidationResults());
		assertEquals("Failed to place order, validation failed", result.getMessage());
	}

	@Test
	public void testValidateOrderWithNonBlockingValidation() throws Throwable {
		IPlaceOrderOption options = mock(IPlaceOrderOption.class);
		PlaceOrderAction<Order, IPlaceOrderOption> action = new PlaceOrderAction<>(order, options);
		OrderValidateResult nonBlockingResult = new OrderValidateResult(OrderValidationState.Pass,
				"Non-blocking validation");
		OrderValidateResult[] results = new OrderValidateResult[] { nonBlockingResult };
		PlaceOrderResult expectedResult = new PlaceOrderResult();

		when(joinPoint.getArgs()).thenReturn(new Object[] { action });
		when(validationService.validate(order)).thenReturn(results);
		when(joinPoint.proceed()).thenReturn(expectedResult);

		PlaceOrderResult result = (PlaceOrderResult) orderValidationAspect.validateOrder(joinPoint);

		assertEquals(expectedResult, result);
		assertEquals(results, result.getValidationResults());
	}

	@Test
	public void testValidateAndProceedWithBlockingValidation() {
		IPlaceOrderOption options = mock(IPlaceOrderOption.class);
		OrderValidateResult blockingResult = new OrderValidateResult(OrderValidationState.Block, "Blocking validation");
		OrderValidateResult[] results = new OrderValidateResult[] { blockingResult };

		when(validationService.validate(order)).thenReturn(results);

		PlaceOrderResult result = orderValidationAspect.validateAndProceed(options, order, mock(Supplier.class));

		assertEquals(OrderStatus.Blocked, result.getStatus());
		assertEquals(results, result.getValidationResults());
		assertEquals("Failed to place order, validation failed", result.getMessage());
	}
}