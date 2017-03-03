package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 19.02.17.
 */

@XMLObject("//doGetCatsDataLimitResponse")
public class DoGetCatsDataLimitResponse {
    @XMLField("catsList/item")
    private List<CatInfoType> catInfoTypeList;

    public List<CatInfoType> getCatInfoTypeList() {
        return catInfoTypeList;
    }
}
