package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class AdvertInfoType {
    @XMLField("serviceId")
    private String serviceId;
    @XMLField("advertId")
    private String advertId;

    public String getServiceId() {
        return serviceId;
    }

    public String getAdvertId() {
        return advertId;
    }
}
