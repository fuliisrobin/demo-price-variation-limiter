package com.fuli.tradingsystem.order.validate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.IPriceVarationLimitStrategy;
import com.fuli.tradingsystem.entities.impl.Instrument;
import com.fuli.tradingsystem.order.validate.service.IPriceVariationLimitStrategyService;

@Component
public class PriceVariationLimitStrategyService implements IPriceVariationLimitStrategyService {
	@Autowired
	SimplePriceVariationLimitStrategyRepository priceVariationLimitStrategyRepo;
	
	@Override
	public IPriceVarationLimitStrategy getPriceVariationLimitStrategy(Instrument instrument) {
		return this.priceVariationLimitStrategyRepo.get(instrument.getSymbol());
	}

}
