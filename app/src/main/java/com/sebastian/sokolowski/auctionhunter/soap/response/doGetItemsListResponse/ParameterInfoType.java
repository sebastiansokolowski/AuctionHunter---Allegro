package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

import java.util.List;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class ParameterInfoType {
    @XMLField("parameterName")
    private String parameterName;
    @XMLField("parameterValue/item")
    private List<String> parameterValue;
    @XMLField("parameterUnit")
    private String parameterUnit;
    @XMLField("parameterIsRange")
    private Boolean parameterIsRange;

    public String getParameterName() {
        return parameterName;
    }

    public List<String> getParameterValue() {
        return parameterValue;
    }

    public String getParameterUnit() {
        return parameterUnit;
    }

    public Boolean getParameterIsRange() {
        return parameterIsRange;
    }
}
