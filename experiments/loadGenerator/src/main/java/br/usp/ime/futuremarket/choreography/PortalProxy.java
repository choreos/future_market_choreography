package br.usp.ime.futuremarket.choreography;

import java.io.IOException;
import java.net.MalformedURLException;

import br.usp.ime.futuremarket.AbstractPortalProxy;
import br.usp.ime.futuremarket.Configuration;

public class PortalProxy extends AbstractPortalProxy {
    private final static FutureMarket MARKET = new FutureMarket(Configuration.getInstance().getRegistryWsdl());

    public PortalProxy() throws IOException {
        super(MARKET);
    }

    @Override
    public br.usp.ime.futuremarket.choreography.Portal getPortal(final int index)
            throws MalformedURLException {
        final String baseAddr = getPortalBaseAddr(index);
        return MARKET.getClient(baseAddr, Portal.class);
    }
}