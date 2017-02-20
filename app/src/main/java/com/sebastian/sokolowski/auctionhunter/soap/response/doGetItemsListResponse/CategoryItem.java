package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

class CategoryItem {
    @XMLField("categoryId")
    private String categoryId;
    @XMLField("categoryName")
    private String categoryName;
    @XMLField("categoryParentId")
    private String categoryParentId;
    @XMLField("categoryItemsCount")
    private String categoryItemsCount;

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryParentId() {
        return categoryParentId;
    }

    public String getCategoryItemsCount() {
        return categoryItemsCount;
    }
}