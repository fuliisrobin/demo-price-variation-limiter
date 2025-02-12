package com.fuli.tradingsystem.entities.impl;

import com.fuli.tradingsystem.entities.InstrumentType;

public class Instrument {
	private InstrumentType type;
	private String symbol;
	
	public Instrument(String symbol, InstrumentType type) {
		this.symbol = symbol;
		this.type = type;
	}

	public InstrumentType getType() {
		return type;
	}

	public void setType(InstrumentType type) {
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
