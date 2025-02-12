package com.fuli.tradingsystem.order.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuli.tradingsystem.entities.impl.StockOrder;
import com.fuli.tradingsystem.order.place.CommonPlaceOrderOptions;
import com.fuli.tradingsystem.order.place.PlaceOrderAction;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.place.service.impl.PlaceStockOrderService;

@RestController
@RequestMapping("/api/stockorder")
public class PlaceOrderController {
	
	@Autowired
	PlaceStockOrderService placeStockOrderService;
	
	@PostMapping("/place")
	public PlaceOrderResult placeStockOrder(@RequestBody PlaceOrderAction<StockOrder, CommonPlaceOrderOptions> placeOrderAction) {
		return this.placeStockOrderService.placeOrder(placeOrderAction);
	}
}
