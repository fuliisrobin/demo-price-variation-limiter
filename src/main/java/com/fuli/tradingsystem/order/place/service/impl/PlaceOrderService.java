package com.fuli.tradingsystem.order.place.service.impl;

import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.order.place.IPlaceOrderOptions;
import com.fuli.tradingsystem.order.place.service.IPlaceOrderService;
import com.fuli.tradingsystem.order.place.service.OrderStatus;
import com.fuli.tradingsystem.order.place.service.PlaceOrderResult;

public class PlaceOrderService<OrderT extends Order, OrderOptionsT extends IPlaceOrderOptions>
	implements IPlaceOrderService<OrderT, OrderOptionsT> {

    /**
     * Dummy place order service.
     */
    public PlaceOrderResult placeOrder(OrderT order, OrderOptionsT orderOptions) {
	PlaceOrderResult result = new PlaceOrderResult();
	if (orderOptions != null && orderOptions.isDryRun()) {
	    result.setStatus(OrderStatus.Skipped);
	    result.setMessage("Order skipped in dry run mode.");
	} else {
	    result.setStatus(OrderStatus.Placed);
	    result.setMessage("Order successfully placed.");
	}
	return result;
    }

}
