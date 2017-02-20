package com.sebastian.sokolowski.auctionhunter.soap;

import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetItemsListEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.DoGetItemsListResponse;

/**
 * Created by Sebastain Sokołowski on 19.02.17.
 */

public class RequestManager {
    private final static String TAG = RequestManager.class.getSimpleName();
    private final String URL = "https://webapi.allegro.pl/service.php";
    private final RequestFactory requestFactory = new RequestFactoryImpl();

    public void deGetItemsList(DoGetItemsListEnvelope doGetItemsListEnvelope, SOAP11Observer<DoGetItemsListResponse> doGetItemsListResponseSOAP11Observer) {
        SOAP11Request<DoGetItemsListResponse> definitionRequest = requestFactory.buildRequest(
                URL,
                doGetItemsListEnvelope.create(),
                "#doGetItemsList",
                DoGetItemsListResponse.class);
        definitionRequest.execute(doGetItemsListResponseSOAP11Observer);
    }
}
