package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.usp.ime.futuremarket.PortalLoader;
import br.usp.ime.futuremarket.Role;

public class PortalLoaderImplOrch implements PortalLoader {

    @Override
    public List<br.usp.ime.futuremarket.choreography.Portal> getPortals() throws IOException {
        final FutureMarket market = new FutureMarket();
        final List<Portal> portalsOrch = market.getClients(Role.PORTAL, Portal.class);

        return portalsOrch2Chor(portalsOrch);
    }

    private List<br.usp.ime.futuremarket.choreography.Portal> portalsOrch2Chor(
            final List<Portal> portalsOrch) {
        final List<br.usp.ime.futuremarket.choreography.Portal> portalsChor = new ArrayList<br.usp.ime.futuremarket.choreography.Portal>();

        for (Portal portalOrch : portalsOrch) {
            portalsChor.add(portalOrch);
        }

        return portalsChor;
    }
}