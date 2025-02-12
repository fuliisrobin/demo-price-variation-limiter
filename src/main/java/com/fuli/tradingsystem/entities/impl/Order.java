package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import com.fuli.tradingsystem.entities.TradeSide;

public class Order {
	@NonNull
    private Instrument instrument;
    @NonNull
    private TradeSide side;
    @NonNull
    private BigDecimal price;
    public Order(Instrument instrument, TradeSide side, BigDecimal price) {
        this.instrument = instrument;
        this.side = side;
        this.price = price;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
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
