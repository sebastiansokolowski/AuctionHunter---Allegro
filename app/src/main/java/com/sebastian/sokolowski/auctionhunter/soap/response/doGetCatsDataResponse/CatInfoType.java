package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

class CatInfoType {
    @XMLField("catId")
    private Integer catId;
    @XMLField("catName")
    private String catName;
    @XMLField("catParent")
    private Integer catParent;
    @XMLField("catPosition")
    private Integer catPosition;
    @XMLField("catIsProductCatalogueEnabled")
    private Integer catIsProductCatalogueEnabled;
}