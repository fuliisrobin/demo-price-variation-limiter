package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.TradeSide;

public class FutureOrder extends Order{

	public FutureOrder(Instrument instrument, TradeSide side, BigDecimal price) {
		super(instrument, side, price);
	}

}
