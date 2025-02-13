package com.fuli.tradingsystem.order.controllers.entities;

import java.math.BigDecimal;

public class StockOrderInput extends OrderInput {
    private BigDecimal quantity;

    public BigDecimal getQuantity() {
	return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
	this.quantity = quantity;
    }

}
