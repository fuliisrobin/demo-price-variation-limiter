package com.fuli.tradingsystem.order.validate.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.ITickTable;
import com.fuli.tradingsystem.entities.impl.Instrument;
import com.fuli.tradingsystem.order.validate.service.ITickTableService;
@Component
public class TickTableService implements ITickTableService {

	@Autowired
	private Map<String, ITickTable> tickTableRepository;
	@Override
	public ITickTable getTickTable(Instrument instrument) {
		// Considering caching tickTable
		return tickTableRepository.get(instrument.getSymbol());
	}

}
