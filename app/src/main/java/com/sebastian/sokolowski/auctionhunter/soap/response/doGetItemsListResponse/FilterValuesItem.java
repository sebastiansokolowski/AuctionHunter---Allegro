package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastian Sokołowski on 06.03.17.
 */

public class FilterValuesItem {
    @XMLField("filterValueId")
    private String filterValueId;
    @XMLField("filterValueName")
    private String filterValueName;
    @XMLField("filterValueCount")
    private String filterValueCount;

    public String getFilterValueId() {
        return filterValueId;
    }

    public String getFilterValueName() {
        return filterValueName;
    }

    public String getFilterValueCount() {
        return filterValueCount;
    }
}
