package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public abstract class AbstractFutureMarket {
	private static final Map<String, Service> CACHE = new HashMap<String, Service>();
	private String registryWsdl;

	private static final Map<String, List<String>> ENDPOINTS = new HashMap<String, List<String>>();
	private static final Map<String, Integer> INDEXES = new HashMap<String, Integer>();

	abstract protected String baseAddressToWsdl(final String baseAddress);

	abstract protected AbstractWSInfo getWSInfo();

	public void setRegistryWsdl(final String wsdl) {
		registryWsdl = wsdl;
	}

	private <T> Map<String, List<T>> getClients(final Role role,
			final Class<T> resultClass) throws IOException {
		final Map<String, List<String>> baseAddresses = getRegistry()
				.getServices(role.toString());
		Map<String, List<T>> clients = new HashMap<String, List<T>>();

		for (Entry<String, List<String>> serviceBaseAddresses : baseAddresses
				.entrySet()) {
			String service = serviceBaseAddresses.getKey();
			clients.put(service, new ArrayList<T>());
			for (String baseAddress : serviceBaseAddresses.getValue()) {
				final AbstractWSInfo info = getWSInfo();
				info.setBaseAddress(baseAddress);
				clients.get(service).add(
						getClient(resultClass, baseAddress, info));
			}
		}
		return clients;
	}

	private <T> T getClient(final String baseAddress, final Class<T> resultClass)
			throws MalformedURLException {
		final AbstractWSInfo info = getWSInfo();
		info.setBaseAddress(baseAddress);

		return getClient(resultClass, baseAddress, info);
	}

	// Esse era private desde o começo
	private <T> T getClient(final Class<T> resultClass,
			final String baseAddress, final AbstractWSInfo info)
			throws MalformedURLException {
		final String wsdl = baseAddressToWsdl(baseAddress);

		checkCache(info, wsdl);
		final Service service = CACHE.get(wsdl);

		return service.getPort(resultClass);
	}

	public <T> T getDependency(String service, Class<T> resultClass)
			throws IOException {
		return nextRoundRobinInstance(service, resultClass);
	}

	public <T> List<T> getDependencyByRole(Role role, Class<T> resultClass)
			throws IOException {
		List<T> resultClients = new ArrayList<T>();

		Map<String, List<T>> clients = getClients(role, resultClass);
		for (Entry<String, List<T>> service : clients.entrySet()) {
			resultClients.add(nextRoundRobinInstance(service.getKey(),
					resultClass));
		}
		return resultClients;
	}

	private <T> T nextRoundRobinInstance(String service, Class<T> resultClass)
			throws IOException {
		return getClient(
				ENDPOINTS.get(service).get(getIndexAndIncrement(service)),
				resultClass);
	}

	private Integer getIndexAndIncrement(String service) {
		if (!INDEXES.containsKey(service)) {
			INDEXES.put(service, 0);
		}
		return INDEXES.put(service,
				(INDEXES.get(service) + 1) % ENDPOINTS.get(service).size());
	}

	protected void clearCache() {
		synchronized (CACHE) {
			CACHE.clear();
		}
	}

	private void checkCache(final AbstractWSInfo info, final String wsdl)
			throws MalformedURLException {
		if (!CACHE.containsKey(wsdl)) {
			final String namespace = info.getNamespace();
			final String serviceName = info.getServiceName();
			cacheService(namespace, serviceName, wsdl);
		}
	}

	private void cacheService(final String namespace, final String serviceName,
			final String wsdl) throws MalformedURLException {
		synchronized (CACHE) {
			if (!CACHE.containsKey(wsdl)) {
				final Service service = createService(namespace, serviceName,
						wsdl);
				CACHE.put(wsdl, service);
			}
		}
	}

	/* Slow */
	private Service createService(final String namespace,
			final String serviceName, final String wsdl)
			throws MalformedURLException {
		final QName qname = new QName(namespace, serviceName);
		final URL url = new URL(wsdl);
		return Service.create(url, qname);
	}

	/**
	 * Public for testing purposes.
	 * 
	 * @return Registry
	 * @throws IOException
	 */
	public Registry getRegistry() throws IOException {
		if (!CACHE.containsKey(registryWsdl)) {
			final AbstractWSInfo info = getWSInfo();
			info.setName("registry");
			checkCache(info, registryWsdl);
		}

		final Service service = CACHE.get(registryWsdl);
		return service.getPort(Registry.class);
	}
}