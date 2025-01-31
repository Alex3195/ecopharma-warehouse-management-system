package uz.duol.ecopharmwarehouse.common;

import com.github.tomakehurst.wiremock.WireMockServer;

public abstract class BaseWiremockTest {

    protected final static WireMockServer wireMockServer = new WireMockServer(10001);

    static {
        wireMockServer.start();
    }
}
