package com.sebastian.sokolowski.auctionhunter.soap.response.doGetCountriesResponse;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

@XMLObject("//doGetCountriesResponse")
public class DoGetCountriesResponse {
    @XMLField("countryArray/item")
    private List<CountryInfoType> countryInfoTypeList;
}
