package br.usp.ime.futuremarket;

import java.io.IOException;

import org.apache.log4j.Logger;

@SuppressWarnings("PMD.MoreThanOneLogger")
public abstract class AbstractLoadGenerator {

    protected static final int THREADS_TIMEOUT = 360;
    protected static final Logger GRAPH = Logger.getLogger("graphsLogger");
    protected static final Logger CONSOLE = Logger.getLogger(AbstractLoadGenerator.class);

    protected int portals, step;
    protected String archType;

    public abstract void generateLoad(final String args[], int start) throws IOException;

    protected AbstractPortalProxy getPortalProxies() throws IOException {
        AbstractPortalProxy proxies;

        if ("orch".equals(archType)) {
            proxies = new br.usp.ime.futuremarket.orchestration.PortalProxy();
        } else {
            proxies = new br.usp.ime.futuremarket.choreography.PortalProxy();
        }

        portals = proxies.size();

        return proxies;
    }
}