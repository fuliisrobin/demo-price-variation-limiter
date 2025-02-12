package com.fuli.tradingsystem.order.validate.service.impl;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.impl.Price;
/**
 * Dummy implementation for demo purpose
 */
@Component("quoteRepository")
public class SimpleQuoteRepository extends ConcurrentHashMap<String, Price>{

	private static final long serialVersionUID = 1L;

	public SimpleQuoteRepository() {
		super();
		this.init();
	}
	public void init() {
		this.addData("HSIZ4", 19010.0, 19020.0, 19000.0);
		this.addData("KS200400F5.KS", 8.88, 8.84, 8.91);
		this.addData("VOD.L", 245.0, 231.0,	240.0);
	}
	private void addData(String symbol, Double lastPrice, Double closePrice, Double theoPrice) {
		Price p = new Price();
		p.setLastPrice(lastPrice == null? null : BigDecimal.valueOf(lastPrice));
		p.setClosePrice(closePrice == null? null : BigDecimal.valueOf(closePrice));
		p.setTheoreticalPrice(theoPrice == null? null : BigDecimal.valueOf(theoPrice));
		this.put(symbol, p);
	}
	
}