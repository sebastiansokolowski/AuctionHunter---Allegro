package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class FilterItem {
    @XMLField("filterId")
    private String filterId;
    @XMLField("filterName")
    private String filterName;
    @XMLField("filterType")
    private String filterType;
    @XMLField("filterControlType")
    private String filterControlType;
    @XMLField("filterDataType")
    private String filterDataType;
    @XMLField("filterIsRange")
    private Boolean filterIsRange;

    public String getFilterId() {
        return filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public String getFilterType() {
        return filterType;
    }

    public String getFilterControlType() {
        return filterControlType;
    }

    public String getFilterDataType() {
        return filterDataType;
    }

    public Boolean getFilterIsRange() {
        return filterIsRange;
    }
}
