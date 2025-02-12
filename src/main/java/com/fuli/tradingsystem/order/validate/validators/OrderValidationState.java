package com.fuli.tradingsystem.order.validate.validators;

/**
 * State enum indicates whether the order should be blocked by the validator.
 */
public enum OrderValidationState {
	Skip(0), Pass(1),  Block(2);
    private int level = 0;
    OrderValidationState(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
}