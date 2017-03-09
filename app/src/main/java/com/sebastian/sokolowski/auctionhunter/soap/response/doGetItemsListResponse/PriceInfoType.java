package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class PriceInfoType {
    public enum PriceTypeEnum {
        PRICE_TYPE_BUY_NOW("buyNow"), PRICE_TYPE_BIDDING("bidding"), PRICE_TYPE_WITH_DELIVERY("withDelivery");

        String value;

        PriceTypeEnum(String value) {
            this.value = value;
        }
    }

    @XMLField("priceType")
    private String priceType;
    @XMLField("priceValue")
    private Float priceValue;

    public PriceTypeEnum getPriceType() {
        for (PriceTypeEnum priceTypeEnum : PriceTypeEnum.values()
                ) {
            if (priceTypeEnum.value.equals(priceType)) {
                return priceTypeEnum;
            }
        }

        return null;
    }

    public Float getPriceValue() {
        return priceValue;
    }
}
