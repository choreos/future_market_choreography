package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.net.MalformedURLException;

import br.usp.ime.futuremarket.AbstractPortalProxy;
import br.usp.ime.futuremarket.choreography.FutureMarket;

public class PortalProxy extends AbstractPortalProxy {

	public PortalProxy() throws IOException {
		super();
		market = new FutureMarket();
		setPortals(market);
	}

	@Override
	public br.usp.ime.futuremarket.choreography.Portal getPortal(final int index)
			throws MalformedURLException {
		final String baseAddr = getPortalBaseAddr(index);
		return market.getClient(baseAddr, Portal.class);
	}
}