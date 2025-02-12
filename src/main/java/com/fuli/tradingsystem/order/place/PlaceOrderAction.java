package com.fuli.tradingsystem.order.place;

import org.springframework.lang.NonNull;

import com.fuli.tradingsystem.entities.impl.Order;

public class PlaceOrderAction<T extends Order, O extends IPlaceOrderOption> {
	@NonNull
	private T order;
	private O options;
	public PlaceOrderAction(T order, O options) {
		this.order = order;
		this.options = options;
	}
	public T getOrder() {
		return order;
	}
	public void setOrder(T order) {
		this.order = order;
	}
	public O getOptions() {
		return options;
	}
	public void setOptions(O options) {
		this.options = options;
	}
}