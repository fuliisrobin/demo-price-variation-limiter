package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.InstrumentType;
import com.fuli.tradingsystem.entities.TradeSide;

public class FutureOrder extends Order{

	public FutureOrder(String symbol, TradeSide side, BigDecimal price) {
		super(new Instrument(symbol, InstrumentType.Future), side, price);
	}

}
