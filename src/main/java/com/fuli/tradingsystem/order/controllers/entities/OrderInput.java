package com.fuli.tradingsystem.order.controllers.entities;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.TradeSide;

public class OrderInput {
    private String symbol;
    private TradeSide side;
    private BigDecimal price;

    public String getSymbol() {
	return symbol;
    }

    public void setSymbol(String symbol) {
	this.symbol = symbol;
    }

    public TradeSide getSide() {
	return side;
    }

    public void setSide(TradeSide side) {
	this.side = side;
    }

    public BigDecimal getPrice() {
	return price;
    }

    public void setPrice(BigDecimal price) {
	this.price = price;
    }

}
