package com.sebastian.sokolowski.auctionhunter.database.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Sebastain Sokołowski on 23.02.17.
 */

public class Target extends RealmObject {
    private String drawerName;
    private String searchingName;
    private String priceMax;
    private String priceMin;
    private String offerType;
    private String condition;
    private RealmList<TargetItem> allItems = new RealmList<>();
    private RealmList<TargetItem> newItems = new RealmList<>();
    private RealmList<TargetItem> favoriteItems = new RealmList<>();

    public RealmList<TargetItem> getAllItems() {
        return allItems;
    }

    public void setAllItems(RealmList<TargetItem> allItems) {
        this.allItems = allItems;
    }

    public RealmList<TargetItem> getNewItems() {
        return newItems;
    }

    public void setNewItems(RealmList<TargetItem> newItems) {
        this.newItems = newItems;
    }

    public RealmList<TargetItem> getFavoriteItems() {
        return favoriteItems;
    }

    public void setFavoriteItems(RealmList<TargetItem> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public Integer getCountAll() {
        return allItems.size();
    }

    public Integer getCountNew() {
        return newItems.size();
    }

    public Integer getCountFavorites() {
        return favoriteItems.size();
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSearchingName() {
        return searchingName;
    }

    public void setSearchingName(String searchingName) {
        this.searchingName = searchingName;
    }

    public void addTargetItemToAllItems(TargetItem targetItem) {
        this.allItems.add(targetItem);
    }

    public void addTargetItemsToAllItems(List<TargetItem> targetItems) {
        this.allItems.addAll(targetItems);
    }

    public void addTargetItemToNewItems(TargetItem targetItem) {
        this.newItems.add(targetItem);
    }

    public void addTargetItemsToNewItems(List<TargetItem> targetItems) {
        this.newItems.addAll(targetItems);
    }

    public void addTargetItemToFavoriteItems(TargetItem targetItem) {
        this.favoriteItems.add(targetItem);
    }

    public void addTargetItemsToFavoriteItems(List<TargetItem> targetItems) {
        this.favoriteItems.addAll(targetItems);
    }
}
