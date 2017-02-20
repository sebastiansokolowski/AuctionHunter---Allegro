package com.sebastian.sokolowski.auctionhunter.soap.request;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class SortOptionsType {
    private SortTypeEnum sortType;
    private SortOrderEnum sortOrder;

    public SortTypeEnum getSortType() {
        return sortType;
    }

    public void setSortType(SortTypeEnum sortType) {
        this.sortType = sortType;
    }

    public SortOrderEnum getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrderEnum sortOrder) {
        this.sortOrder = sortOrder;
    }
}
