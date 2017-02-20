package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataCountResponse;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

@XMLObject("//doGetCatsDataCountResponse")
public class DoGetCatsDataCountResponse {
    @XMLField("catsCount")
    private Integer catsCount;
    @XMLField("verKey")
    private Long verKey;
    @XMLField("verStr")
    private String verStr;
}
