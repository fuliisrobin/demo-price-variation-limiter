package com.fuli.tradingsystem.order.validate.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuli.tradingsystem.entities.ITickTable;
import com.fuli.tradingsystem.entities.InstrumentType;
import com.fuli.tradingsystem.entities.PriceVariationScenario;
import com.fuli.tradingsystem.entities.PriceVariationType;
import com.fuli.tradingsystem.entities.TradeSide;
import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.entities.impl.OrderUtils;
import com.fuli.tradingsystem.entities.impl.TickTable;
import com.fuli.tradingsystem.order.validate.service.IPriceVariationLimitStrategyService;
import com.fuli.tradingsystem.order.validate.service.IQuoteService;
import com.fuli.tradingsystem.order.validate.service.ITickTableService;
import com.fuli.tradingsystem.order.validate.service.impl.PriceVarationLimitStrategy;

class PriceVariationLimiterOrderValidatorTest {
	Logger logger = LoggerFactory.getLogger(getClass());
    @InjectMocks
    private PriceVariationLimiterOrderValidator validator;

    @Mock
    private IQuoteService quoteService;
    @Mock
    private ITickTableService tickTableService;
    @Mock
    private IPriceVariationLimitStrategyService priceLimitStrategyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testValidateOptionDataInDoc() {
        PriceVarationLimitStrategy strategy = createStrategy("TickSize", 8.0, "Advantage");
        ITickTable tickTable = new TickTable(new double[] {0.0, 10.0}, new double[] {0.01, 0.05});
        for(int i = 0; i < rawOptionsTestData.length; i++) {
            String[] data = rawOptionsTestData[i].split(", ");
            Order order = createOrder(data[1], data[3], data[4], Double.parseDouble(data[5]));
            OrderValidateResult result = PriceVariationLimiterOrderValidator.validatePriceVariation(order, strategy, new BigDecimal(data[6]), tickTable);
            logger.info(i + "," + result.message());
            assertEquals(
        		new BigDecimal(data[8]).setScale(1), 
        		new BigDecimal(extractField(result.message(), "ActualVariation")).multiply(BigDecimal.valueOf(-1)).setScale(1),
        		String.format("ActualVariation is not expected at data[%d]", i)
            );
        }
    }

    @Test
    void testValidateStockDataInDoc() {
        PriceVarationLimitStrategy strategy = createStrategy("Absolute", 8.0, "Both");
        for(int i = 0; i < rawStocksTestData.length; i++) {
            String[] data = rawStocksTestData[i].split(", ");
            Order order = createOrder(data[1], data[3], data[4], Double.parseDouble(data[5]));
            OrderValidateResult result = PriceVariationLimiterOrderValidator.validatePriceVariation(order, strategy, new BigDecimal(data[6]), null);
            logger.info(i + "," + result.message());
            assertEquals(
        		Double.parseDouble(data[8]), 
        		Math.abs(Double.parseDouble(extractField(result.message(), "ActualVariation"))),
        		String.format("ActualVariation is not expected at data[%d]", i)
            );
        }
    }
    Order createOrder(String instrumentType, String symbol, String side, double price) {
        Order order = OrderUtils.createOrder(InstrumentType.valueOf(instrumentType), symbol, TradeSide.valueOf(side), price, 1)  ;
        return order;
    }
    PriceVarationLimitStrategy createStrategy(String strStrategy, double variationValue, String strScenario) {
        PriceVarationLimitStrategy strategy = new PriceVarationLimitStrategy();
        strategy.setScenario(PriceVariationScenario.valueOf(strScenario));
        strategy.setType(PriceVariationType.valueOf(strStrategy));
        strategy.setValue(BigDecimal.valueOf(variationValue));
        return strategy;
    }
    String extractField(String str, String field) {
    	for(String rawField : str.split(",")) {
    		if(rawField.indexOf(field+":") == 0) {
    			return rawField.substring(field.length()+1);
    		}
    		
    	}
    	return "";
    	
    }
    
    String[] rawOptionsTestData = {
   		// strategy, type, variation, symbol, side, price, reference price, expected result, expected variation
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 8.81, 8.81, Pass, 0",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 8.72, 8.81, Block, 9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 8.90, 8.81, Pass, -9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 8.92, 8.91, Pass, -1",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 8.82, 8.91, Pass, 9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 9.00, 8.91, Block, -9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 9.94, 9.93, Pass, -1",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 9.84, 9.93, Block, 9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 10.10, 9.93, Pass, -9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 9.94, 9.95, Pass, 1",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 9.87, 9.95, Pass, 8",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 10.20, 9.95, Block, -9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 10.10, 10.15, Pass, 1",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 9.94, 10.15, Block, 9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Buy, 10.60, 10.15, Pass, -9", // original data is 10.06, should be 10.60
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 10.30, 10.25, Pass, -1",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 9.96, 10.25, Pass, 9",
    		"TickSize, Option, 8.0,  KS200400F5.KS, Sell, 10.70, 10.25, Block, -9"
    };
    String[] rawStocksTestData = {
    		"Absolute, Stock, 10.0, VOD.L, Buy, 245.0, 245.0, Pass, 0",
    		"Absolute, Stock, 10.0, VOD.L, Buy, 255.0, 245.0, Block, 10",
    		"Absolute, Stock, 10.0, VOD.L, Buy, 265.0, 245.0, Block, 20",
    		"Absolute, Stock, 10.0, VOD.L, Sell, 245.0, 245.0, Pass, 0",
    		"Absolute, Stock, 10.0, VOD.L, Sell, 235.0, 245.0, Block, 10",
    		"Absolute, Stock, 10.0, VOD.L, Sell, 225.0, 245.0, Block, 20"
    };
}
