package com.sebastian.sokolowski.auctionhunter.database.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastain Sokołowski on 23.02.17.
 */

public class Target {
    private String drawerName;
    private Integer countAll;
    private List<TargetItem> targetItemList = new ArrayList<>();

    public List<TargetItem> getTargetItemList() {
        return targetItemList;
    }

    public void setTargetItemList(List<TargetItem> targetItemList) {
        this.targetItemList = targetItemList;
    }

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
        return targetItemList.size();
    }
}
