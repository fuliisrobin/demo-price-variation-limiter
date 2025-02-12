package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.TradeSide;

public class OptionOrder extends Order{

    private BigDecimal quantity;
    public OptionOrder(Instrument instrument, TradeSide side, BigDecimal price, BigDecimal quantity) {
        super(instrument, side, price);
        this.quantity = quantity;
    }
    
    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
