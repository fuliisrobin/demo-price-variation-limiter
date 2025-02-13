package com.fuli.tradingsystem.order.validate.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.validate.service.IOrderValidationService;
import com.fuli.tradingsystem.order.validate.validators.IOrderValidator;
import com.fuli.tradingsystem.order.validate.validators.OrderValidateResult;
import com.fuli.tradingsystem.order.validate.validators.OrderValidationState;

@SpringBootTest
@ContextConfiguration(classes = { OrderValidationService.class })
public class OrderValidationServiceTests {

    private OrderValidationService orderValidationService;

    @BeforeEach
    public void setUp() {
	// Initialize the service with mocked validators
	OrderValidationService service = new OrderValidationService();
	service.setValidators(new ArrayList<>());
	orderValidationService = service;
    }

    @Test
    public void testValidate_AllPass() {
	// Mock data
	Order order = mock(Order.class);

	List<IOrderValidator> validatorList = new ArrayList<>();
	{
	    IOrderValidator validator = mock(IOrderValidator.class);
	    when(validator.validate(order)).thenReturn(new OrderValidateResult(OrderValidationState.Pass, "Pass msg"));
	    validatorList.add(validator);
	}
	// Inject mocked validators into the service
	orderValidationService.setValidators(validatorList);

	// Perform validation
	OrderValidateResult[] results = orderValidationService.validate(order);

	// Assertions
	assertEquals(1, results.length);
	assertEquals(OrderValidationState.Pass, results[0].level());
    }

    @Test
    public void testValidate_FirstFails() {
	// Mock data
	Order order = mock(Order.class);
	List<IOrderValidator> validatorList = new ArrayList<>();
	{
	    IOrderValidator validator = mock(IOrderValidator.class);
	    when(validator.validate(order))
		    .thenReturn(new OrderValidateResult(OrderValidationState.Block, "Block msg"));
	    validatorList.add(validator);
	}
	{
	    IOrderValidator validator = mock(IOrderValidator.class);
	    when(validator.validate(order)).thenReturn(new OrderValidateResult(OrderValidationState.Pass, "Pass msg"));
	    validatorList.add(validator);
	}
	// Inject mocked validators into the service
	((OrderValidationService) orderValidationService).setValidators(validatorList);

	// Perform validation
	OrderValidateResult[] results = orderValidationService.validate(order);

	// Assertions
	assertEquals(1, results.length);
	assertEquals(OrderValidationState.Block, results[0].level());
    }
}