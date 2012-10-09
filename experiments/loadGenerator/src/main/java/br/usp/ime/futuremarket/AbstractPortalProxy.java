package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public abstract class AbstractPortalProxy {
    private List<String> portals;

    public AbstractPortalProxy(final AbstractFutureMarket market) throws IOException {
        portals = market.getBaseAddresses(Role.PORTAL);

        while (portals.size() == 0) {
            System.out.println("# WARNING: 0 portals in registry. Waiting 1 sec...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            portals = market.getBaseAddresses(Role.PORTAL);
        }
    }

    protected String getPortalBaseAddr(final int index) {
        final int safeIndex = index % portals.size();
        return portals.get(safeIndex);
    }

    public abstract br.usp.ime.futuremarket.choreography.Portal getPortal(final int index)
            throws MalformedURLException;

    public int size() {
        return portals.size();
    }
}