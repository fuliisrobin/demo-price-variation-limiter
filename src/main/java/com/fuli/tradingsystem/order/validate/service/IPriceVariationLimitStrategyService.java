package com.fuli.tradingsystem.order.validate.service;

import com.fuli.tradingsystem.entities.IPriceVariationLimitStrategy;
import com.fuli.tradingsystem.entities.impl.Instrument;

public interface IPriceVariationLimitStrategyService {
	public IPriceVariationLimitStrategy getPriceVariationLimitStrategy(Instrument instrument);
}
