package com.fuli.tradingsystem.entities;

import java.math.BigDecimal;

public interface IPriceVariationLimitStrategy {
    PriceVariationType getType();
    BigDecimal getValue();
    PriceVariationScenario getScenario();
}
