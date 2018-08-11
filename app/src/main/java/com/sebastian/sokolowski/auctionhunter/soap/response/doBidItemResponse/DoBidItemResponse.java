package com.sebastian.sokolowski.auctionhunter.soap.response.doBidItemResponse;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

@XMLObject("//doBidItemResponse")
public class DoBidItemResponse {
    @XMLField("bidPrice")
    private String bidPrice;

    public String getBidPrice() {
        return bidPrice;
    }
}
