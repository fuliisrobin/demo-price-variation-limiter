package com.fuli.tradingsystem.entities.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fuli.tradingsystem.entities.ITickTable;

public class TickTableTest {
    private ITickTable tickTable;

    @BeforeEach
    public void setUp() {
	BigDecimal[] segments = new BigDecimal[] { BigDecimal.valueOf(0.0), BigDecimal.valueOf(10.0),
		BigDecimal.valueOf(20.0), BigDecimal.valueOf(30.0), BigDecimal.valueOf(40.0) };
	BigDecimal[] tickSizes = new BigDecimal[] { BigDecimal.valueOf(0.01), BigDecimal.valueOf(0.05),
		BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.25), BigDecimal.valueOf(0.5) };
	tickTable = new TickTable(segments, tickSizes);
    }

    @Test
    public void testGetTicksVariationSameSegment() {
	BigDecimal orderPrice = BigDecimal.valueOf(15.0);
	BigDecimal referencePrice = BigDecimal.valueOf(12.0);
	BigDecimal expectedTicks = BigDecimal.valueOf(3.0).divide(BigDecimal.valueOf(0.05));
	assertEquals(expectedTicks, tickTable.getTicksVariation(orderPrice, referencePrice));
    }

    @Test
    public void testGetTicksVariationDifferentSegments() {
	BigDecimal orderPrice = BigDecimal.valueOf(5.0);
	BigDecimal referencePrice = BigDecimal.valueOf(25.0);
	BigDecimal expectedTicks = BigDecimal.valueOf(5.0).divide(BigDecimal.valueOf(0.01))
		.add(BigDecimal.valueOf(10.0).divide(BigDecimal.valueOf(0.05)))
		.add(BigDecimal.valueOf(5.0).divide(BigDecimal.valueOf(0.1)));
	assertEquals(expectedTicks, tickTable.getTicksVariation(orderPrice, referencePrice));
    }

    @Test
    public void testGetTicksVariationEdgeCase() {
	BigDecimal orderPrice = BigDecimal.valueOf(10.0);
	BigDecimal referencePrice = BigDecimal.valueOf(20.0);
	BigDecimal expectedTicks = BigDecimal.valueOf(10.0).divide(BigDecimal.valueOf(0.05));
	assertEquals(expectedTicks.setScale(3), tickTable.getTicksVariation(orderPrice, referencePrice).setScale(3));
    }

    @Test
    public void testGetTicksVariationSamePrice() {
	BigDecimal orderPrice = BigDecimal.valueOf(15.0);
	BigDecimal referencePrice = BigDecimal.valueOf(15.0);
	BigDecimal expectedTicks = BigDecimal.ZERO;
	assertEquals(expectedTicks.setScale(2), tickTable.getTicksVariation(orderPrice, referencePrice).setScale(2));
    }

    @Test
    public void testGetTicksVariationInvalidPrice() {
	BigDecimal orderPrice = BigDecimal.valueOf(-5.0);
	BigDecimal referencePrice = BigDecimal.valueOf(25.0);
	assertThrows(IllegalArgumentException.class, () -> tickTable.getTicksVariation(orderPrice, referencePrice));
    }
}