package com.fuli.tradingsystem.order.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuli.tradingsystem.entities.impl.FutureOrder;
import com.fuli.tradingsystem.entities.impl.OptionOrder;
import com.fuli.tradingsystem.entities.impl.StockOrder;
import com.fuli.tradingsystem.order.controllers.entities.FutureOrderInput;
import com.fuli.tradingsystem.order.controllers.entities.OptionOrderInput;
import com.fuli.tradingsystem.order.controllers.entities.StockOrderInput;
import com.fuli.tradingsystem.order.place.PlaceOrderOptions;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;
import com.fuli.tradingsystem.order.place.service.impl.PlaceFutureOrderService;
import com.fuli.tradingsystem.order.place.service.impl.PlaceOptionOrderService;
import com.fuli.tradingsystem.order.place.service.impl.PlaceStockOrderService;

@RestController
@RequestMapping("/api")
public class PlaceOrderController {

    @Autowired
    PlaceStockOrderService placeStockOrderService;
    @Autowired
    PlaceOptionOrderService placeOptionOrderService;
    @Autowired
    PlaceFutureOrderService placeFutureOrderService;

    @PostMapping("/stock/order/place")
    public PlaceOrderResult placeStockOrder(@RequestBody StockOrderInput input) {
	StockOrder order = new StockOrder(input.getSymbol(), input.getSide(), input.getPrice(), input.getQuantity());
	PlaceOrderOptions options = new PlaceOrderOptions();
	options.setDryRun(input.isDryRun());
	
	// We can also build OrderOption using this input
	return this.placeStockOrderService.placeOrder(order, options);
    }

    @PostMapping("/option/order/place")
    public PlaceOrderResult placeOptionOrder(@RequestBody OptionOrderInput input) {
	OptionOrder order = new OptionOrder(input.getSymbol(), input.getSide(), input.getPrice(), input.getQuantity());
	PlaceOrderOptions options = new PlaceOrderOptions();
	options.setDryRun(input.isDryRun());
	return this.placeOptionOrderService.placeOrder(order, options);
    }

    @PostMapping("/future/order/place")
    public PlaceOrderResult placeFutureOrder(@RequestBody FutureOrderInput input) {
	FutureOrder order = new FutureOrder(input.getSymbol(), input.getSide(), input.getPrice());
	PlaceOrderOptions options = new PlaceOrderOptions();
	options.setDryRun(input.isDryRun());
	return this.placeFutureOrderService.placeOrder(order, options);
    }
}
