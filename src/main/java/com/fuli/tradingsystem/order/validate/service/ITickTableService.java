package com.fuli.tradingsystem.order.validate.service;

import com.fuli.tradingsystem.entities.ITickTable;
import com.fuli.tradingsystem.entities.impl.Instrument;

public interface ITickTableService {
    public ITickTable getTickTable(Instrument instrument);
}
