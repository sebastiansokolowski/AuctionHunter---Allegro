package com.sebastian.sokolowski.auctionhunter.database.entity;

/**
 * Created by Sebastain Sokołowski on 23.02.17.
 */

public class Target {
    private String drawerName;
    private Integer countAll;
    private Integer countNow;

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }

    public Integer getCountNow() {
        return countNow;
    }

    public void setCountNow(Integer countNow) {
        this.countNow = countNow;
    }
}
