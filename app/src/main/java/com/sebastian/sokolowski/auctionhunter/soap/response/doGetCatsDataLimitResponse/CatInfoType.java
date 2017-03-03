package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class CatInfoType {
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

    public Integer getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public Integer getCatParent() {
        return catParent;
    }

    public Integer getCatPosition() {
        return catPosition;
    }

    public Integer getCatIsProductCatalogueEnabled() {
        return catIsProductCatalogueEnabled;
    }
}