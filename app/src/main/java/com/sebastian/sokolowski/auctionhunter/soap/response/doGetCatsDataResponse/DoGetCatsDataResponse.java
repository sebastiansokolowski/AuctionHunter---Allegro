package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataResponse;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

@XMLObject("//doGetCatsDataResponse")
public class DoGetCatsDataResponse {
    @XMLField("catsList/item")
    private List<CatInfoType> catInfoTypeList;
    @XMLField("verKey")
    private Long verKey;
    @XMLField("verStr")
    private String verStr;
}
