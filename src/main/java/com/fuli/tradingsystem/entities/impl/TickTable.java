package com.fuli.tradingsystem.entities.impl;

import java.math.BigDecimal;
import java.util.Arrays;

import com.fuli.tradingsystem.entities.ITickTable;

public class TickTable implements ITickTable {
    private BigDecimal[] segments;
    private BigDecimal[] tickSizes;

    public TickTable(BigDecimal[] segments, BigDecimal[] tickSizes) {
        this.segments = Arrays.copyOf(segments, segments.length);
        this.tickSizes = Arrays.copyOf(tickSizes, tickSizes.length);
        this.validateInput();
    }

    public TickTable(double[] segments, double[] tickSizes) {
        this.segments = Arrays.stream(segments)
                .mapToObj(BigDecimal::valueOf)
                .toArray(BigDecimal[]::new);
        this.tickSizes = Arrays.stream(tickSizes)
                .mapToObj(BigDecimal::valueOf)
                .toArray(BigDecimal[]::new);
    	this.validateInput();
    }

    /**
     * Segments should be incrementing and non-negative.
     * Tick sizes should be positive.
     */
    private void  validateInput() {
        if (segments == null || segments.length == 0 || tickSizes == null || tickSizes.length == 0) {
            throw new IllegalArgumentException("Segments and tick sizes must be non-empty arrays.");
        }
        if (segments.length != tickSizes.length) {
            throw new IllegalArgumentException("Segments and tick sizes arrays must be of the same length.");
        }
        Arrays.stream(segments).reduce((s1, s2) -> {
            if(s1.compareTo(s2) >= 0) {
                throw new IllegalArgumentException("Segments must be incrementing.");
            }
            return s2;
        });

        if (segments[0].compareTo(BigDecimal.ZERO) < 0 || tickSizes[0].compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Segments must be non-negative.");
        }
    }
    
    @Override
	public BigDecimal getTicksVariation(BigDecimal orderPrice, BigDecimal referencePrice) {
    	if(orderPrice.compareTo(BigDecimal.ZERO) < 0) {
    		throw new IllegalArgumentException("Order price must be positive");
    	}
    	if(referencePrice.compareTo(BigDecimal.ZERO) < 0) {
    		throw new IllegalArgumentException("Reference price must be positive");
    	}
    	
    	// find min / max value of orderPrice and referencePrice
        BigDecimal minPrice = orderPrice.min(referencePrice), 
        		maxPrice = orderPrice.max(referencePrice);

        // Arrays.binarySearch to find the segment for min and max index for 
        int maxIndex = findSegmentIndex(maxPrice), minIndex = findSegmentIndex(minPrice);
        
        // In same segment
        if(minIndex == maxIndex) {
            return maxPrice.subtract(minPrice).divide(tickSizes[minIndex]);
        } else {
            BigDecimal ticks = BigDecimal.ZERO;
            for(int i = minIndex; i <= maxIndex; i++) {
            	// segments[i] is the lower boundary of the tick size at index i
                if(i == minIndex) {
                    ticks = ticks.add(segments[i+1].subtract(minPrice).divide(tickSizes[i]));
                } else if(i == maxIndex) {
                    ticks = ticks.add(maxPrice.subtract(segments[i]).divide(tickSizes[i]));
                } else {// i > minIndex && i < maxIndex
                    ticks = ticks.add(segments[i].subtract(segments[i-1]).divide(tickSizes[i]));
                }
            }
            return ticks;
        }
    }
    
    
    int findSegmentIndex(BigDecimal price) {
    	int index = Arrays.binarySearch(segments, price);
    	return index > 0 ? index: -index - 2;
    }
}