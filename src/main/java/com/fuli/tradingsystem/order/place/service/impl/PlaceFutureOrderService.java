package com.fuli.tradingsystem.order.place.service.impl;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.FutureOrder;
import com.fuli.tradingsystem.order.place.PlaceOrderOptions;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.annotations.ValidateOrder;

@Component("placeFutureOrderService")
public class PlaceFutureOrderService extends PlaceOrderService<FutureOrder, PlaceOrderOptions> {

    @Override
    @ValidateOrder
    public PlaceOrderResult placeOrder(FutureOrder order, PlaceOrderOptions orderOptions) {
	// Future order specific logic goes here
	return super.placeOrder(order, orderOptions);
    }

}
