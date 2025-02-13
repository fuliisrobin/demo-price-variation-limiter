package com.fuli.tradingsystem.order.place.service.impl;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.StockOrder;
import com.fuli.tradingsystem.order.place.PlaceOrderOptions;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.annotations.ValidateOrder;

@Component("placeStockOrderService")
public class PlaceStockOrderService extends PlaceOrderService<StockOrder, PlaceOrderOptions> {
    
    @Override
    @ValidateOrder
    public PlaceOrderResult placeOrder(StockOrder order, PlaceOrderOptions orderOptions) {
	// Stock order specific logic goes here
	return super.placeOrder(order, orderOptions);
    }
}
