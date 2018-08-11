package com.sebastian.sokolowski.auctionhunter.soap.response.doLoginResponse;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

@XMLObject("//doLoginResponse")
public class DoLoginResponse {
    @XMLField("sessionHandlePart")
    private String sessionHandlePart;

    @XMLField("userId")
    private Long userId;

    @XMLField("serverTime")
    private Long serverTime;

    public String getSessionHandlePart() {
        return sessionHandlePart;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getServerTime() {
        return serverTime;
    }
}
