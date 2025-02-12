package com.fuli.tradingsystem.order.place.service.impl;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.FutureOrder;
import com.fuli.tradingsystem.order.place.CommonPlaceOrderOptions;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.annotations.ValidateOrder;

@Component("placeFutureOrderService")
public class PlaceFutureOrderService extends PlaceOrderService<FutureOrder, CommonPlaceOrderOptions> {

	@Override
	@ValidateOrder
	public PlaceOrderResult placeOrder(PlaceOrderAction<FutureOrder, CommonPlaceOrderOptions> action) {
		return super.placeOrder(action);
	}

}
