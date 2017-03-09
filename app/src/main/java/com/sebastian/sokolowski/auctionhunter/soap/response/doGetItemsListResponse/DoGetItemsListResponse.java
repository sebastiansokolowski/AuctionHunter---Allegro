package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 19.02.17.
 */

@XMLObject("//doGetItemsListResponse")
public class DoGetItemsListResponse {
    @XMLField("itemsCount")
    private Integer itemsCount;
    @XMLField("itemsFeaturedCount")
    private Integer itemsFeaturedCount;
    @XMLField("itemsList/item")
    private List<Item> itemList;
    @XMLField("categoriesList/categoriesTree/item")
    private List<CategoryItem> categoryItemList;
    @XMLField("filtersList/item")
    private List<FilterItem> filterItemList;

    public Integer getItemsCount() {
        return itemsCount;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public Integer getItemsFeaturedCount() {
        return itemsFeaturedCount;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public List<FilterItem> getFilterItemList() {
        return filterItemList;
    }
}
