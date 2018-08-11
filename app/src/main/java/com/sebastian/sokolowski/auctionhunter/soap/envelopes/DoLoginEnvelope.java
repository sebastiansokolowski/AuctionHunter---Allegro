package com.sebastian.sokolowski.auctionhunter.soap.envelopes;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;


/**
 * Created by Sebastian Sokołowski on 19.02.17.
 */

public class DoLoginEnvelope extends BaseEnvelope {

    private String userLogin;

    private String userPassword;

    private Long localVersion;

    public DoLoginEnvelope() {
    }

    public SOAPEnvelope create() {
        XMLParentNode defineInDict = getBody().addNode(
                NAMESPACE, "DoLoginRequest");
        defineInDict.addTextNode(NAMESPACE, "userLogin", userLogin);
        defineInDict.addTextNode(NAMESPACE, "userPassword", userPassword);
        defineInDict.addTextNode(NAMESPACE, "countryCode", countryId.toString());
        defineInDict.addTextNode(NAMESPACE, "webapiKey", webApiKey);
        defineInDict.addTextNode(NAMESPACE, "localVersion", localVersion.toString());
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setLocalVersion(Long localVersion) {
        this.localVersion = localVersion;
    }
}
