package com.sebastian.sokolowski.auctionhunter.database.models;

import io.realm.RealmObject;

/**
 * Created by Sebastian Sokołowski on 06.03.17.
 */

public class FilterValueModel extends RealmObject {
    private String filterValueId;
    private String filterValueName;
    private String filterValueCount;

    public String getFilterValueId() {
        return filterValueId;
    }

    public void setFilterValueId(String filterValueId) {
        this.filterValueId = filterValueId;
    }

    public String getFilterValueName() {
        return filterValueName;
    }

    public void setFilterValueName(String filterValueName) {
        this.filterValueName = filterValueName;
    }

    public String getFilterValueCount() {
        return filterValueCount;
    }

    public void setFilterValueCount(String filterValueCount) {
        this.filterValueCount = filterValueCount;
    }
}
