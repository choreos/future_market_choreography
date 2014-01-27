package br.usp.ime.futuremarket;

import java.util.List;

public class EnactmentEngineImpl implements EnactmentEngine {

	protected AbstractFutureMarket market = null;
	protected final String serviceName;

	public EnactmentEngineImpl(final String serviceName,
			final AbstractFutureMarket market) {
		this.serviceName = serviceName;
		this.market = market;
	}

	@Override
	public String setInvocationAddress(final String registryRole,
			final String name, final List<String> registryEndpoints) {
		String wsdl = null;
		final String registryEndpoint = registryEndpoints.get(0);

		if (registryEndpoint.endsWith("/")) {
			wsdl = registryEndpoint.substring(0, registryEndpoint.length() - 1)
					+ "?wsdl";
		} else {
			wsdl = registryEndpoint + "?wsdl";
		}

		market.setRegistryWsdl(wsdl);

		/*
		 * Now done by the Registry.setInvocationAddress()
		 */

		return "OK";
	}

}