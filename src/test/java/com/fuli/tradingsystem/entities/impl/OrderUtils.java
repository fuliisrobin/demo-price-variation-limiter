package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.InstrumentType;
import com.fuli.tradingsystem.entities.TradeSide;

public class OrderUtils {

	public static Order createOrder(InstrumentType instrumentType, String symbol, TradeSide side, double price,
			double quantity) {
		switch (instrumentType) {
		case Stock:
			return new StockOrder(symbol, side, BigDecimal.valueOf(price),
				BigDecimal.valueOf(quantity));
		case Future: 
			return new FutureOrder(symbol, side, BigDecimal.valueOf(price));
		case Option:
			return new OptionOrder(symbol, side, BigDecimal.valueOf(price),
					BigDecimal.valueOf(quantity));
		default:
			throw new RuntimeException("Instrument type not supported ");
		}
	}
}
