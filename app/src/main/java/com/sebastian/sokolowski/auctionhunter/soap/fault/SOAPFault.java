package com.sebastian.sokolowski.auctionhunter.soap.fault;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

/**
 * Created by Sebastain Sokołowski on 20.02.17.
 */

public class SOAPFault extends SOAP11Fault {
    @XMLField("faultcode")
    private String faultcode;

    @XMLField("faultstring")
    private String faultstring;

    @XMLField("faultactor")
    private String faultactor;
}
