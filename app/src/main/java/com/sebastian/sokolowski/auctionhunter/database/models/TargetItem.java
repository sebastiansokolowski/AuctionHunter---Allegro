package com.sebastian.sokolowski.auctionhunter.database.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sebastian Sokołowski on 01.03.17.
 */

public class TargetItem extends RealmObject {
    @PrimaryKey
    private Long id;
    private String offertype;
    private String url;
    private String name;
    private String imageUrl;
    private Float price;
    private Float priceFull;
    private Float priceBid;
    private String when;

    public enum Offertype {BUY_NOW, AUCTION, BOTH}

    public Offertype getOffertype() {
        if (offertype == null || offertype.equals("")) {
            return null;
        }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetItem that = (TargetItem) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
