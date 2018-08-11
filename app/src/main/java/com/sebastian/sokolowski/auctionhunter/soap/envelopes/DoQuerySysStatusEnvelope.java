package com.sebastian.sokolowski.auctionhunter.soap.envelopes;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;


/**
 * Created by Sebastian Sokołowski on 19.02.17.
 */

public class DoQuerySysStatusEnvelope extends BaseEnvelope {

    private Integer sysvar;

    public DoQuerySysStatusEnvelope() {
    }

    public SOAPEnvelope create() {
        XMLParentNode defineInDict = getBody().addNode(
                NAMESPACE, "DoQuerySysStatusRequest");
        defineInDict.addTextNode(NAMESPACE, "sysvar", sysvar.toString());
        defineInDict.addTextNode(NAMESPACE, "countryId", countryId.toString());
        defineInDict.addTextNode(NAMESPACE, "webapiKey", webApiKey);
        return this;
    }

    public void setSysvar(Integer sysvar) {
        this.sysvar = sysvar;
    }
}
