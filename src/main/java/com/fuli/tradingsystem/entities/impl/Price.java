package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;


/**
 * Price entity which include price of instrument.
 */
public class Price {

	BigDecimal lastPrice;
    BigDecimal theoreticalPrice;
    BigDecimal closePrice;
    
    /**
     * Use prices in this order:
     * lastPrice > closePrice > theoreticalPrice
     * @return reference price as BigDecimal, if returns null means no reference price.
     */
    public BigDecimal getReferencePrice() {
    	if(lastPrice != null) {
    		return lastPrice;
    	} else if(closePrice !=null) {
    		return closePrice;
    	} else if(theoreticalPrice != null) {
    		return theoreticalPrice;
    	} else {
    		return null;
    	}
    }
    
    public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getTheoreticalPrice() {
		return theoreticalPrice;
	}

	public void setTheoreticalPrice(BigDecimal theoreticalPrice) {
		this.theoreticalPrice = theoreticalPrice;
	}

	public BigDecimal getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}
}
