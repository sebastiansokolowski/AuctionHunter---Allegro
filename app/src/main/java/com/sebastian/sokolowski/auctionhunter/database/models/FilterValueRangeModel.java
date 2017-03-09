package com.sebastian.sokolowski.auctionhunter.database.models;

import io.realm.RealmObject;

/**
 * Created by Sebastian Sokołowski on 06.03.17.
 */

public class FilterValueRangeModel extends RealmObject {
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
