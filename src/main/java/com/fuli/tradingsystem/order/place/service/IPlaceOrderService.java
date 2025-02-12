package com.fuli.tradingsystem.order.place.service;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.place.IPlaceOrderOption;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;

public interface IPlaceOrderService<OrderT extends Order, OptionsT extends IPlaceOrderOption> {
	public PlaceOrderResult placeOrder(PlaceOrderAction<OrderT, OptionsT> action);
}
