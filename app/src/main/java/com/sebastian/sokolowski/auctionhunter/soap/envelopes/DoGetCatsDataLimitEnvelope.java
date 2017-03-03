package com.sebastian.sokolowski.auctionhunter.soap.envelopes;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;


/**
 * Created by Sebastian Sokołowski on 19.02.17.
 */

public class DoGetCatsDataLimitEnvelope extends BaseEnvelope {

    private Integer offset;
    private Integer packageElement;

    public DoGetCatsDataLimitEnvelope() {
    }

    public SOAPEnvelope create() {
        XMLParentNode defineInDict = getBody().addNode(
                NAMESPACE, "DoGetCatsDataLimitRequest");
        defineInDict.addTextNode(NAMESPACE, "countryId", countryId.toString());
        defineInDict.addTextNode(NAMESPACE, "webapiKey", webApiKey);
        if (offset != null) {
            defineInDict.addTextNode(NAMESPACE, "offset", offset.toString());
        }
        if (packageElement != null) {
            defineInDict.addTextNode(NAMESPACE, "packageElement", packageElement.toString());
        }
        return this;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setPackageElement(Integer packageElement) {
        this.packageElement = packageElement;
    }
}
