package com.sebastian.sokolowski.auctionhunter.database.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sebastian Sokołowski on 03.03.17.
 */

public class Cat extends RealmObject {
    @PrimaryKey
    private Integer catId;
    private String catName;
    private Integer catParent;
    private Integer catPosition;
    private Integer catIsProductCatalogueEnabled;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Integer getCatParent() {
        return catParent;
    }

    public void setCatParent(Integer catParent) {
        this.catParent = catParent;
    }

    public Integer getCatPosition() {
        return catPosition;
    }

    public void setCatPosition(Integer catPosition) {
        this.catPosition = catPosition;
    }

    public Integer getCatIsProductCatalogueEnabled() {
        return catIsProductCatalogueEnabled;
    }

    public void setCatIsProductCatalogueEnabled(Integer catIsProductCatalogueEnabled) {
        this.catIsProductCatalogueEnabled = catIsProductCatalogueEnabled;
    }
}
