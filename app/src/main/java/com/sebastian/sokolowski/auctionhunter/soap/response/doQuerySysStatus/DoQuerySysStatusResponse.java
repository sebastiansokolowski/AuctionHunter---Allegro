package com.sebastian.sokolowski.auctionhunter.soap.response.doQuerySysStatus;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

@XMLObject("//doQuerySysStatusResponse")
public class DoQuerySysStatusResponse {
    @XMLField("info")
    private String info;

    @XMLField("verKey")
    private Long verKey;

    public String getInfo() {
        return info;
    }

    public Long getVerKey() {
        return verKey;
    }
}
