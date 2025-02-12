package com.fuli.tradingsystem.order.validate.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.ITickTable;
import com.fuli.tradingsystem.entities.impl.TickTable;
/**
 * Dummy implementation for demo purpose
 */
@Component("tickTableRepository")
public class SimpleTickTableRepository extends ConcurrentHashMap<String, ITickTable>{

	private static final long serialVersionUID = 1L;

	public SimpleTickTableRepository() {
		super();
		this.init();
	}
	
	public void init() {
		ITickTable tt = new TickTable(new double[] {0.0, 10.0}, new double[] {0.01, 0.05});
		this.put("KS200400F5.KS", tt);
	}
	
}