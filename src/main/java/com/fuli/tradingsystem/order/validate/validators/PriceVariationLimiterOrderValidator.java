package com.fuli.tradingsystem.order.validate.validators;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fuli.tradingsystem.entities.IPriceVariationLimitStrategy;
import com.fuli.tradingsystem.entities.ITickTable;
import com.fuli.tradingsystem.entities.PriceVariationScenario;
import com.fuli.tradingsystem.entities.PriceVariationType;
import com.fuli.tradingsystem.entities.TradeSide;
import com.fuli.tradingsystem.entities.impl.Instrument;
import com.fuli.tradingsystem.entities.impl.Order;
import com.fuli.tradingsystem.entities.impl.Price;
import com.fuli.tradingsystem.order.validate.service.IPriceVariationLimitStrategyService;
import com.fuli.tradingsystem.order.validate.service.IQuoteService;
import com.fuli.tradingsystem.order.validate.service.ITickTableService;

/**
 * Core validator of price variation.
 */
@Component
@org.springframework.core.annotation.Order(2)
public class PriceVariationLimiterOrderValidator implements IOrderValidator {
    @Autowired
    IQuoteService quoteService;
    @Autowired
    ITickTableService tickTableService;
    @Autowired
    IPriceVariationLimitStrategyService priceLimitStrategyService;

    /**
     * Validate if the order's price variation is under a threshold. Basic fields of
     * the order should have already been validated
     */
    @Override
    public OrderValidateResult validate(Order order) {
	Instrument instrument = order.getInstrument();
	IPriceVariationLimitStrategy strategy = priceLimitStrategyService.getPriceVariationLimitStrategy(instrument);

	// TODO Question: if no price limit strategy for this instrument, should the
	// order proceed?
	if (strategy == null) {
	    return new OrderValidateResult(OrderValidationState.Skip,
		    String.format("No price limit strategy found for instrument %s", instrument.getSymbol()));
	} else if (strategy.getScenario() == PriceVariationScenario.Skip) { // TODO is this `Skip` necessary?
	    return new OrderValidateResult(OrderValidationState.Skip,
		    String.format("Price limit strategy is \"SKIP\" for instrument %s", instrument.getSymbol()));
	} else {
	    Price price = quoteService.getPrice(instrument);
	    if (price == null || price.getReferencePrice() == null) {
		return new OrderValidateResult(OrderValidationState.Block,
			String.format("Reference price is not available for instrument %s.", instrument.getSymbol()));
	    }
	    return this.validatePriceVariation(order, strategy, price.getReferencePrice());
	}
    }

    public OrderValidateResult validatePriceVariation(Order order, IPriceVariationLimitStrategy strategy,
	    BigDecimal referencePrice) {

	return validatePriceVariation(order, strategy, referencePrice,
		strategy.getType() == PriceVariationType.TickSize
			? this.tickTableService.getTickTable(order.getInstrument())
			: null);
    }

    /**
     * Static (stateless) method to validate the order, need all the information
     * needed for the validation.
     * 
     * @param order          Order object
     * @param strategy       Strategy object
     * @param referencePrice reference Price, calculation logic see
     *                       com.fuli.tradingsystem.entities.impl.Price
     * @param tickTable
     * @return Validation result as OrderValidateResult
     */
    public static OrderValidateResult validatePriceVariation(Order order, IPriceVariationLimitStrategy strategy,
	    BigDecimal referencePrice, ITickTable tickTable) {
	// Use price - reference price, slightly different from the document.
	BigDecimal priceVariation, priceDiff = order.getPrice().subtract(referencePrice);
	PriceVariationType variationType = strategy.getType();

	StringBuilder alertMsg = new StringBuilder(String.format("VariationType:%s,AllowedVariation:%s,Scenario:%s",
		variationType.name(), strategy.getValue().setScale(2).toString(), strategy.getScenario().name()));
	if (variationType == PriceVariationType.Absolute) {
	    priceVariation = priceDiff;
	} else if (variationType == PriceVariationType.Percentage) {
	    priceVariation = priceDiff.divide(referencePrice);
	} else if (variationType == PriceVariationType.TickSize) {
	    if (tickTable == null) {
		return new OrderValidateResult(OrderValidationState.Block,
			alertMsg.append("Tick table not available, Block.").toString());
	    }
	    priceVariation = tickTable.getTicksVariation(order.getPrice(), referencePrice)
		    .multiply(BigDecimal.valueOf(priceDiff.signum()));
	} else {
	    throw new IllegalArgumentException("Illegal PriceVariationType when validating order price variation.");
	}
	alertMsg.append(",Price:").append(order.getPrice().setScale(2));
	alertMsg.append(",ReferencePrice:").append(referencePrice.setScale(2));
	alertMsg.append(String.format(",ActualVariation:%s", priceVariation.setScale(2).toString()));

	// Intentionally put this as uninitialized, to avoid missing branch before using
	// this variable.
	OrderValidationState validationResult;
	if (priceVariation.abs().compareTo(strategy.getValue()) >= 0) {
	    alertMsg.append(",").append(order.getSide().name())
		    .append(priceVariation.signum() > 0 ? " higher" : " lower");

	    if (strategy.getScenario() == PriceVariationScenario.Both) {
		validationResult = OrderValidationState.Block;
	    } else if (strategy.getScenario() == PriceVariationScenario.Advantage) {
		if (order.getSide() == TradeSide.Buy) {
		    validationResult = priceVariation.signum() > 0 ? /* Buy High, Pass */ OrderValidationState.Pass
			    : /* Buy Low, Block */OrderValidationState.Block;
		} else if (order.getSide() == TradeSide.Sell) {
		    validationResult = priceVariation.signum() > 0 ? /* Sell High, Block */ OrderValidationState.Block
			    : /* Sell Low, Pass */OrderValidationState.Pass;
		} else {
		    throw new IllegalArgumentException(
			    "Side of Orderside can only be Buy or Sell when executing variation validation.");
		}
	    } else if (strategy.getScenario() == PriceVariationScenario.Disadvantage) {
		if (order.getSide() == TradeSide.Buy) {
		    validationResult = priceVariation.signum() > 0 ? /* Buy High, Block */ OrderValidationState.Block
			    : /* Buy Low, Pass */OrderValidationState.Pass;
		} else if (order.getSide() == TradeSide.Sell) {
		    validationResult = priceVariation.signum() > 0 ? /* Sell High, Pass */ OrderValidationState.Pass
			    : /* Sell Low, Block */OrderValidationState.Block;
		} else {
		    throw new IllegalArgumentException(
			    "Side of Orderside can only be Buy or Sell when executing variation validation.");
		}
	    } else {
		throw new IllegalArgumentException(
			"PriceVariationScenario can not be Skip when executing variation validation.");
	    }
	} else {
	    alertMsg.append(",").append(order.getSide().name());
	    validationResult = OrderValidationState.Pass;
	}
	alertMsg.append(",").append(validationResult.name());
	return new OrderValidateResult(validationResult, alertMsg.toString());
    }
}
