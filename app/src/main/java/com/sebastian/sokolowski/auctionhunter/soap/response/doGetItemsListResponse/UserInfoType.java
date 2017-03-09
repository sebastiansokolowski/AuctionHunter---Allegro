package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class UserInfoType {
    @XMLField("userId")
    private Integer userId;
    @XMLField("userLogin")
    private String userLogin;
    @XMLField("userRating")
    private Integer userRating;
    @XMLField("userIcons")
    private Integer userIcons;
    @XMLField("countryId")
    private Integer countryId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public Integer getUserIcons() {
        return userIcons;
    }

    public void setUserIcons(Integer userIcons) {
        this.userIcons = userIcons;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}
