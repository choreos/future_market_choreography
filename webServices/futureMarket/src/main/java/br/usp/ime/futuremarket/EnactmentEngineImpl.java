package br.usp.ime.futuremarket;

import java.io.IOException;

import javax.jws.WebMethod;

import br.usp.ime.futuremarket.choreography.FutureMarket;

public class EnactmentEngineImpl implements EnactmentEngine {

    protected FutureMarket market = null;

    @Override
    @WebMethod
    public String setInvocationAddress(final String role, final String registryEndpoint)
            throws IOException {
        final String wsdl = registryEndpoint + "?wsdl";
        market = new FutureMarket(wsdl);
        market.register(role);
        return "OK";
    }

}