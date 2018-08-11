package com.sebastian.sokolowski.auctionhunter.soap.envelopes;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;


/**
 * Created by Sebastian Sokołowski on 19.02.17.
 */

public class DoBidItemEnvelope extends BaseEnvelope {

    private String sessionHandle;

    private Long bidItId;

    private Float bidUserPrice;

    private Long bidQuantity;

    private Long bidBuyNow;

    public DoBidItemEnvelope() {
    }

    public SOAPEnvelope create() {
        XMLParentNode defineInDict = getBody().addNode(
                NAMESPACE, "DoBidItemRequest");
        defineInDict.addTextNode(NAMESPACE, "sessionHandle", sessionHandle);
        defineInDict.addTextNode(NAMESPACE, "bidItId", bidItId.toString());
        defineInDict.addTextNode(NAMESPACE, "bidUserPrice", bidUserPrice.toString());
        defineInDict.addTextNode(NAMESPACE, "bidQuantity", bidQuantity.toString());
        defineInDict.addTextNode(NAMESPACE, "bidBuyNow", bidBuyNow.toString());
        return this;
    }

    public void setSessionHandle(String sessionHandle) {
        this.sessionHandle = sessionHandle;
    }

    public void setBidItId(Long bidItId) {
        this.bidItId = bidItId;
    }

    public void setBidUserPrice(Float bidUserPrice) {
        this.bidUserPrice = bidUserPrice;
    }

    public void setBidQuantity(Long bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public void setBidBuyNow(Long bidBuyNow) {
        this.bidBuyNow = bidBuyNow;
    }
}
