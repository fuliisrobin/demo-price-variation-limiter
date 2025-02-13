package com.fuli.tradingsystem.order.validate.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.Instrument;
import com.fuli.tradingsystem.entities.impl.Price;
import com.fuli.tradingsystem.order.validate.service.IQuoteService;

@Component("quoteService")
public class QuoteService implements IQuoteService {
    // Dummy implementation for demo purpose
    @Autowired
    private Map<String, Price> quoteRepository;

    @Override
    public Price getPrice(Instrument instrument) {
	return quoteRepository.get(instrument.getSymbol());
    }

}
