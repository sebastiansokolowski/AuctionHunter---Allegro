package com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.adapter;

/**
 * Created by Sebastian Sokołowski on 04.03.17.
 */

public class CatAdapterItem {
    private Integer id;
    private String name;
    private boolean hasChild;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
