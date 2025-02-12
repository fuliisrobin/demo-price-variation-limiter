package com.fuli.tradingsystem.order.place.service;

import com.fuli.tradingsystem.order.validate.validators.OrderValidateResult;

public class PlaceOrderResult {
	private String message;
	private OrderStatus status;
	private OrderValidateResult[] validationResults;
	
	public void setValidationResults(OrderValidateResult[] validationResults) {
		this.validationResults = validationResults;
	}
	public PlaceOrderResult() {
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public OrderValidateResult[] getValidationResults() {
		return validationResults;
	}
	
}
