package com.fuli.tradingsystem.entities;

import java.math.BigDecimal;

public interface ITickTable {
	public BigDecimal getTicksVariation(BigDecimal orderPrice, BigDecimal referencePrice);
}