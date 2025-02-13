package com.fuli.tradingsystem.order.validate.service.impl;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.IPriceVariationLimitStrategy;
import com.fuli.tradingsystem.entities.PriceVariationScenario;
import com.fuli.tradingsystem.entities.PriceVariationType;

/**
 * Dummy implementation for demo purpose
 */
@Component
public class SimplePriceVariationLimitStrategyRepository
	extends ConcurrentHashMap<String, IPriceVariationLimitStrategy> {
    private static final long serialVersionUID = 1L;

    public SimplePriceVariationLimitStrategyRepository() {
	this.init();
    }

    public void init() {
	{
	    PriceVarationLimitStrategy strategy = new PriceVarationLimitStrategy();
	    strategy.setScenario(PriceVariationScenario.Disadvantage);
	    strategy.setType(PriceVariationType.TickSize);
	    strategy.setValue(BigDecimal.valueOf(8));

	    this.put("KS200400F5.KS", strategy);
	}
	{
	    PriceVarationLimitStrategy strategy = new PriceVarationLimitStrategy();
	    strategy.setScenario(PriceVariationScenario.Both);
	    strategy.setType(PriceVariationType.Absolute);
	    strategy.setValue(BigDecimal.valueOf(10));

	    this.put("VOD.L", strategy);
	}
    }
}
