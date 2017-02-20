package com.sebastian.sokolowski.auctionhunter.soap.request;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class RangeValueType {
    private String rangeValueMin;
    private String rangeValueMax;

    public String getRangeValueMin() {
        return rangeValueMin;
    }

    public void setRangeValueMin(String rangeValueMin) {
        this.rangeValueMin = rangeValueMin;
    }

    public String getRangeValueMax() {
        return rangeValueMax;
    }

    public void setRangeValueMax(String rangeValueMax) {
        this.rangeValueMax = rangeValueMax;
    }
}
