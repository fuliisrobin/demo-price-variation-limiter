package com.fuli.tradingsystem.order.validate.service;

import com.fuli.tradingsystem.entities.IPriceVarationLimitStrategy;
import com.fuli.tradingsystem.entities.impl.Instrument;

public interface IPriceVariationLimitStrategyService {
	public IPriceVarationLimitStrategy getPriceVariationLimitStrategy(Instrument instrument);
}
