package com.sebastian.sokolowski.auctionhunter.soap.envelopes;

import com.alexgilleran.icesoap.xml.XMLParentNode;


/**
 * Created by Sebastian Sokołowski on 19.02.17.
 */

public class DoGetCatsDataCountEnvelope extends BaseEnvelope {
    public DoGetCatsDataCountEnvelope() {
        XMLParentNode defineInDict = getBody().addNode(
                NAMESPACE, "DoGetCatsDataCountRequest");
        defineInDict.addTextNode(NAMESPACE, "countryId", countryId.toString());
        defineInDict.addTextNode(NAMESPACE, "webapiKey", webApiKey);
    }
}
