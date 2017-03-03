package com.sebastian.sokolowski.auctionhunter.soap;

import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
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

    public void deGetItemsList(DoGetItemsListEnvelope doGetItemsListEnvelope, SOAPObserver<DoGetItemsListResponse, AllegroSOAPFault> doGetItemsListResponseAllegroSOAPFaultSOAPObserver) {
        Request<DoGetItemsListResponse, AllegroSOAPFault> definitionRequest = requestFactory.buildRequest(
                URL,
                doGetItemsListEnvelope.create(),
                "#doGetItemsList",
                DoGetItemsListResponse.class, AllegroSOAPFault.class);
        definitionRequest.execute(doGetItemsListResponseAllegroSOAPFaultSOAPObserver);
    }

    public void doGetCatsDataCount(SOAPObserver<DoGetCatsDataCountResponse, AllegroSOAPFault> doGetCatsDataCountResponseAllegroSOAPFaultSOAPObserver) {
        Request<DoGetCatsDataCountResponse, AllegroSOAPFault> definitionRequest = requestFactory.buildRequest(
                URL,
                new DoGetCatsDataCountEnvelope(),
                "#doGetCatsDataCount",
                DoGetCatsDataCountResponse.class, AllegroSOAPFault.class);
        definitionRequest.execute(doGetCatsDataCountResponseAllegroSOAPFaultSOAPObserver);
    }

    public void doGetCatsDataLimit(DoGetCatsDataLimitEnvelope doGetCatsDataLimitEnvelope, SOAPObserver<DoGetCatsDataLimitResponse, AllegroSOAPFault> doGetCatsDataLimitResponseAllegroSOAPFaultSOAPObserver) {
        Request<DoGetCatsDataLimitResponse, AllegroSOAPFault> definitionRequest = requestFactory.buildRequest(
                URL,
                doGetCatsDataLimitEnvelope.create(),
                "#doGetCatsDataLimit",
                DoGetCatsDataLimitResponse.class, AllegroSOAPFault.class);
        definitionRequest.execute(doGetCatsDataLimitResponseAllegroSOAPFaultSOAPObserver);
    }
}
