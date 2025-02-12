package com.fuli.tradingsystem.order.validate.service.impl;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.IPriceVarationLimitStrategy;
import com.fuli.tradingsystem.entities.PriceVariationScenario;
import com.fuli.tradingsystem.entities.PriceVariationType;

public class PriceVarationLimitStrategy implements IPriceVarationLimitStrategy {
	
	private PriceVariationType type;
	private BigDecimal value;
	private PriceVariationScenario scenario;
	
	public PriceVariationType getType() {
		return type;
	}
	public void setType(PriceVariationType type) {
		this.type = type;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public PriceVariationScenario getScenario() {
		return scenario;
	}
	public void setScenario(PriceVariationScenario scenario) {
		this.scenario = scenario;
	}

}
