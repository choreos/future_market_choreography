package br.usp.ime.futuremarket;

import java.io.IOException;

public class EnactmentEngineImpl implements EnactmentEngine {

    protected AbstractFutureMarket market = null;
    protected final String serviceName;

    public EnactmentEngineImpl(final String serviceName, final AbstractFutureMarket market) {
        this.serviceName = serviceName;
        this.market = market;
    }

    @Override
    public String setInvocationAddress(final String registryRole, final String registryEndpoint)
            throws IOException {
        final String wsdl = registryEndpoint + "?wsdl";

        market.setRegistryWsdl(wsdl);
        market.register(this.serviceName);

        return "OK";
    }
}