package com.fuli.tradingsystem.order.place.service.impl;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.OptionOrder;
import com.fuli.tradingsystem.order.place.CommonPlaceOrderOptions;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.annotations.ValidateOrder;

@Component("placeOptionOrderService")
public class PlaceOptionOrderService extends PlaceOrderService<OptionOrder, CommonPlaceOrderOptions> {

	@Override
	@ValidateOrder
	public PlaceOrderResult placeOrder(PlaceOrderAction<OptionOrder, CommonPlaceOrderOptions> action) {
		return super.placeOrder(action);
	}

}
