package com.sebastian.sokolowski.auctionhunter.soap.envelopes;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.sebastian.sokolowski.auctionhunter.BuildConfig;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class BaseEnvelope extends BaseSOAP11Envelope {
    protected final static String NAMESPACE = "https://webapi.allegro.pl/service.php";
    protected final static Integer countryId = 1;
    protected final static String webApiKey = BuildConfig.ALLEGRO_WEBAPI_KEY;

    public BaseEnvelope() {
    }
}
