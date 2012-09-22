package br.usp.ime.futuremarket.choreography;

import java.io.IOException;
import java.util.List;

import br.usp.ime.futuremarket.PortalLoader;
import br.usp.ime.futuremarket.Role;

public class PortalLoaderImplChor implements PortalLoader {

    @Override
    public List<Portal> getPortals() throws IOException {
        final FutureMarket market = new FutureMarket();
        return market.getClients(Role.PORTAL, Portal.class);
    }
}