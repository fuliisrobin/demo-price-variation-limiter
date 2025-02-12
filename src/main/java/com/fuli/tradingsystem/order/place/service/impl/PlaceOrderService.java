package com.fuli.tradingsystem.order.place.service.impl;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.place.IPlaceOrderOption;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;
import com.fuli.tradingsystem.order.place.service.IPlaceOrderService;
import com.fuli.tradingsystem.order.place.service.OrderStatus;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;

public class PlaceOrderService<OrderT extends Order, OptionsT extends IPlaceOrderOption> implements IPlaceOrderService<OrderT, OptionsT> {

	/**
	 * Dummy place order service.
	 */
	public PlaceOrderResult placeOrder(PlaceOrderAction<OrderT, OptionsT> action) {
		PlaceOrderResult result = new PlaceOrderResult();
		result.setStatus(OrderStatus.Placed);
		result.setMessage("Order successfully placed.");
		return result;
	}
}
