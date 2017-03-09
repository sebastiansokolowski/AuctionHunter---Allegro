package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

import java.util.List;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class Item {
    @XMLField("itemId")
    private Long itemId;
    @XMLField("itemTitle")
    private String itemTitle;
    @XMLField("leftCount")
    private Integer leftCount;
    @XMLField("bidsCount")
    private Integer bidsCount;
    @XMLField("biddersCount")
    private Integer biddersCount;
    @XMLField("quantityType")
    private String quantityType;
    @XMLField("endingTime")
    private String endingTime;
    @XMLField("timeToEnd")
    private String timeToEnd;
    @XMLField("categoryId")
    private Integer categoryId;
    @XMLField("conditionInfo")
    private String conditionInfo;
    @XMLField("promotionInfo")
    private Integer promotionInfo;
    @XMLField("additionalInfo")
    private Integer additionalInfo;
    @XMLField("sellerInfo")
    private UserInfoType sellerInfo;
    @XMLField("priceInfo/item")
    private List<PriceInfoType> priceInfo;
    @XMLField("photosInfo/item")
    private List<PhotoInfoType> photosInfo;
    @XMLField("parametersInfo/item")
    private List<ParameterInfoType> parametersInfo;
    @XMLField("advertInfo")
    private AdvertInfoType advertInfo;

    public Long getItemId() {
        return itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public Integer getBidsCount() {
        return bidsCount;
    }

    public Integer getBiddersCount() {
        return biddersCount;
    }

    public String getQuantityType() {
        return quantityType;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public String getTimeToEnd() {
        return timeToEnd;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getConditionInfo() {
        return conditionInfo;
    }

    public Integer getPromotionInfo() {
        return promotionInfo;
    }

    public Integer getAdditionalInfo() {
        return additionalInfo;
    }

    public UserInfoType getSellerInfo() {
        return sellerInfo;
    }

    public List<PriceInfoType> getPriceInfo() {
        return priceInfo;
    }

    public List<PhotoInfoType> getPhotosInfo() {
        return photosInfo;
    }

    public List<ParameterInfoType> getParametersInfo() {
        return parametersInfo;
    }

    public AdvertInfoType getAdvertInfo() {
        return advertInfo;
    }
}
