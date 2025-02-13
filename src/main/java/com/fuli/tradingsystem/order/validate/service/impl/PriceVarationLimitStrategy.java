package com.fuli.tradingsystem.order.validate.service.impl;

import java.math.BigDecimal;

import com.fuli.tradingsystem.entities.IPriceVariationLimitStrategy;
import com.fuli.tradingsystem.entities.PriceVariationScenario;
import com.fuli.tradingsystem.entities.PriceVariationType;

/**
 * This is the strategy used by the 
 */
public class PriceVarationLimitStrategy implements IPriceVariationLimitStrategy {
	
	// Percentage, Absolute, TickSize
	private PriceVariationType type;
	// Max variation value
	private BigDecimal value;
	// Advantage, Disadvantage, Both, Skip
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
