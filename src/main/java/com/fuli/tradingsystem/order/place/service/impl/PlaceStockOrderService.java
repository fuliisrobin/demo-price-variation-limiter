package com.fuli.tradingsystem.order.place.service.impl;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.StockOrder;
import com.fuli.tradingsystem.order.place.CommonPlaceOrderOptions;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.validate.annotations.ValidateOrder;

@Component("placeStockOrderService")
public class PlaceStockOrderService extends PlaceOrderService<StockOrder, CommonPlaceOrderOptions> {
	@Override
	@ValidateOrder
	public PlaceOrderResult placeOrder(PlaceOrderAction<StockOrder, CommonPlaceOrderOptions> action) {
		return super.placeOrder(action);
	}
}
