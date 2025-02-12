package com.fuli.tradingsystem.entities;

import java.math.BigDecimal;

public interface IPriceVarationLimitStrategy {
    PriceVariationType getType();
    BigDecimal getValue();
    PriceVariationScenario getScenario();
}
