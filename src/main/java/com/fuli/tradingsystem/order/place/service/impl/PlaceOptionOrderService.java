package com.fuli.tradingsystem.order.place.service.impl;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.OptionOrder;
import com.fuli.tradingsystem.order.place.PlaceOrderOptions;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.annotations.ValidateOrder;

@Component("placeOptionOrderService")
public class PlaceOptionOrderService extends PlaceOrderService<OptionOrder, PlaceOrderOptions> {
    @Override
    @ValidateOrder
    public PlaceOrderResult placeOrder(OptionOrder order, PlaceOrderOptions orderOptions) {
	// Option order specific logic goes here

	return super.placeOrder(order, orderOptions);
    }

}
