package com.sebastian.sokolowski.auctionhunter.soap.request;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class FilterOptionsType {
    private String filterId;
    private List<String> filterValueIdList = new ArrayList<>();
    private RangeValueType filterValueRange;

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public List<String> getFilterValueId() {
        return filterValueIdList;
    }

    public void addFilterValueId(String filterValueId) {
        this.filterValueIdList.add(filterValueId);
    }

    public void addFilterValuesId(List<String> filterValueIdList) {
        this.filterValueIdList.addAll(filterValueIdList);
    }

    public RangeValueType getFilterValueRange() {
        return filterValueRange;
    }

    public void setFilterValueRange(RangeValueType filterValueRange) {
        this.filterValueRange = filterValueRange;
    }
}
