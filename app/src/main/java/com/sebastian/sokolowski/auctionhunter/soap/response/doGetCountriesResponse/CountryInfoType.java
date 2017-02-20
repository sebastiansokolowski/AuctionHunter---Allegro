package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCountriesResponse;

import com.alexgilleran.icesoap.annotation.XMLField;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

class CountryInfoType {
    @XMLField("countryId")
    private Integer countryId;
    @XMLField("countryName")
    private String countryName;
}
