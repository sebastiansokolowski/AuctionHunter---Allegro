package com.sebastian.sokolowski.auctionhunter.database.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sebastian Sokołowski on 01.03.17.
 */

public class TargetItem extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String offertype;
    private String url;
    private String name;
    private String imageUrl;
    private String price;
    private String priceFull;
    private String priceBid;
    private String when;

    public enum Offertype {BUY_NOW, AUCTION, BOTH}

    public Offertype getOffertype() {
        return Offertype.valueOf(offertype);
    }

    public void setOffertype(Offertype offertype) {
        this.offertype = offertype.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceFull() {
        return priceFull;
    }

    public void setPriceFull(String priceFull) {
        this.priceFull = priceFull;
    }

    public String getPriceBid() {
        return priceBid;
    }

    public void setPriceBid(String priceBid) {
        this.priceBid = priceBid;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
