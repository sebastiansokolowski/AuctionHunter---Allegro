package com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastian Sokołowski on 09.03.17.
 */

public class PhotoInfoType {
    public enum PhotoTypeEnum {
        PHOTO_TYPE_SMALL("small"), PHOTO_TYPE_MEDIUM("medium"), PHOTO_TYPE_LARGE("large");

        String value;

        PhotoTypeEnum(String value) {
            this.value = value;
        }
    }

    @XMLField("photoSize")
    private String photoSize;
    @XMLField("photoUrl")
    private String photoUrl;
    @XMLField("photoIsMain")
    private Boolean photoIsMain;

    public PhotoTypeEnum getPhotoSize() {
        for (PhotoTypeEnum photoTypeEnum : PhotoTypeEnum.values()
                ) {
            if (photoTypeEnum.value.equals(photoSize)) {
                return photoTypeEnum;
            }
        }

        return null;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Boolean getPhotoIsMain() {
        return photoIsMain;
    }
}
