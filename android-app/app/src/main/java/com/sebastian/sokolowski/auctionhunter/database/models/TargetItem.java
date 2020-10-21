package com.sebastian.sokolowski.auctionhunter.database.models;

import com.sebastian.sokolowski.auctionhunter.rest.response.SellingModeFormat;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sebastian Sokołowski on 01.03.17.
 */

public class TargetItem extends RealmObject {
    @PrimaryKey
    private String id;
    private String url;
    private String sellingModeFormat;
    private String name;
    private String imageUrl;
    private Float price;
    private Float priceFull;
    private Float priceBid;
    private String when;

    public SellingModeFormat getSettingModeFormat() {
        if (sellingModeFormat == null || sellingModeFormat.equals("")) {
            return null;
        }
        return SellingModeFormat.valueOf(sellingModeFormat);
    }

    public void setSellingModeFormat(SellingModeFormat sellingModeFormat) {
        this.sellingModeFormat = sellingModeFormat.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPriceFull() {
        return priceFull;
    }

    public void setPriceFull(Float priceFull) {
        this.priceFull = priceFull;
    }

    public Float getPriceBid() {
        return priceBid;
    }

    public void setPriceBid(Float priceBid) {
        this.priceBid = priceBid;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        TargetItem that = (TargetItem) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
