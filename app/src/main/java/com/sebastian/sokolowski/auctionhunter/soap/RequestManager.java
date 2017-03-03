package com.sebastian.sokolowski.auctionhunter.soap;

import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetCatsDataCountEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetCatsDataLimitEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetItemsListEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataCountResponse.DoGetCatsDataCountResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse.DoGetCatsDataLimitResponse;
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

    public void doGetCatsDataCount(SOAP11Observer<DoGetCatsDataCountResponse> doGetCatsDataCountResponseSOAP11Observer){
        SOAP11Request<DoGetCatsDataCountResponse> definitionRequest = requestFactory.buildRequest(
                URL,
                new DoGetCatsDataCountEnvelope(),
                "#doGetCatsDataCount",
                DoGetCatsDataCountResponse.class);
        definitionRequest.execute(doGetCatsDataCountResponseSOAP11Observer);
    }

    public void doGetCatsDataLimit(DoGetCatsDataLimitEnvelope doGetCatsDataLimitEnvelope, SOAPObserver<DoGetCatsDataLimitResponse, AllegroSOAPFault> doGetCatsDataLimitResponseSOAP11Observer){
        Request<DoGetCatsDataLimitResponse,AllegroSOAPFault> definitionRequest = requestFactory.buildRequest(
                URL,
                doGetCatsDataLimitEnvelope.create(),
                "#doGetCatsDataLimit",
                DoGetCatsDataLimitResponse.class, AllegroSOAPFault.class);
        definitionRequest.execute(doGetCatsDataLimitResponseSOAP11Observer);
    }
}
