package com.fuli.tradingsystem.order.validate.service;

import com.fuli.tradingsystem.entities.impl.Instrument;
import com.fuli.tradingsystem.entities.impl.Price;

public interface IQuoteService {

	Price getPrice(Instrument instrument);

}
