package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.InstrumentType;
import com.fuli.tradingsystem.entities.TradeSide;

public class OptionOrder extends Order{

    private BigDecimal quantity;
    public OptionOrder(String symbol, TradeSide side, BigDecimal price, BigDecimal quantity) {
        super(new Instrument(symbol, InstrumentType.Option), side, price);
        this.quantity = quantity;
    }
    
    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
