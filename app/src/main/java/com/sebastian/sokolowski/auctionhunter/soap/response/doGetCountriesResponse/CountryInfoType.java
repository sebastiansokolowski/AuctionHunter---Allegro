package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCountriesResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class CountryInfoType {
    @XMLField("countryId")
    private Integer countryId;
    @XMLField("countryName")
    private String countryName;

    public Integer getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }
}
