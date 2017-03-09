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
    private Integer categoryId;
    private RealmList<TargetItem> allItems = new RealmList<>();
    private RealmList<TargetItem> newItems = new RealmList<>();
    private RealmList<TargetItem> favoriteItems = new RealmList<>();
    private RealmList<FilterModel> filterModels = new RealmList<>();

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

    public RealmList<FilterModel> getFilterModels() {
        return filterModels;
    }

    public void addFilterModel(FilterModel filterModel) {
        this.filterModels.add(filterModel);
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}
