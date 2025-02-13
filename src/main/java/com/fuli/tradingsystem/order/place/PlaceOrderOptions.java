package com.fuli.tradingsystem.order.place;

public class PlaceOrderOptions implements IPlaceOrderOptions{
    private boolean dryRun;

    public boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }
}
