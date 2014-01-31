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
		
		final Map<String, List<String>> roleServices = RegistryUtils
				.toMap(getRegistry().getServices(role.toString()));
		
		Map<String, List<T>> clients = new HashMap<String, List<T>>();

		for (Entry<String, List<String>> service : roleServices
				.entrySet()) {

			String serviceName = service.getKey();
			clients.put(serviceName, new ArrayList<T>());
			
			for (String endpoint : service.getValue()) {
				
				final AbstractWSInfo info = getWSInfo();
				info.setEndpoint(endpoint);
				info.setRole(role);
				
				clients.get(serviceName).add(
						getClient(resultClass, endpoint, info));
			}
		}
		return clients;
	}

	private <T> T getClient(final String serviceName, final String endpoint, final Class<T> resultClass)
			throws MalformedURLException {
		final AbstractWSInfo info = getWSInfo();
		info.setEndpoint(endpoint);
		info.setName(serviceName);
		return getClient(resultClass, endpoint, info);
	}

	private <T> T getClient(final Class<T> resultClass, final String endpoint,
			final AbstractWSInfo info) throws MalformedURLException {
		final String wsdl = endpoint + "?wsdl";

		checkCache(info, wsdl);
		final Service service = CACHE.get(wsdl);

		return service.getPort(resultClass);
	}

	public <T> T getDependency(String serviceName, Class<T> resultClass)
			throws IOException {
		initializeEndpoints(serviceName);
		return nextRoundRobinInstance(serviceName, resultClass);
	}

	private void initializeEndpoints(String service) throws IOException {
		if (!ENDPOINTS.containsKey(service) || ENDPOINTS.get(service) == null) {
			ENDPOINTS.put(service, getRegistry().getInstances(service));
			INDEXES.put(service, 0);
		}
	}

	public <T> List<T> getDependencyByRole(Role role, Class<T> resultClass)
			throws IOException {
		List<T> resultClients = new ArrayList<T>();
		Map<String, List<String>> services = RegistryUtils.toMap(getRegistry()
				.getServices(role.toString()));
		Map<String, List<T>> clients = getClients(role, resultClass);

		for (Entry<String, List<String>> service : services.entrySet()) {
			initializeEndpoints(service.getKey());
		}

		for (Entry<String, List<T>> service : clients.entrySet()) {
			resultClients.add(nextRoundRobinInstance(service.getKey(),
					resultClass));
		}
		return resultClients;
	}

	private <T> T nextRoundRobinInstance(String service, Class<T> resultClass)
			throws IOException {
		Integer index = getIndexAndIncrement(service);
		if (index == -1)
			return null;
		return getClient(service,
				ENDPOINTS.get(service).get(index),
				resultClass);
	}

	private Integer getIndexAndIncrement(String service) {
		Integer currentIndex = INDEXES.get(service);
		List<String> serviceEndpoints = ENDPOINTS.get(service);
		int numberOfServiceEndpoints = serviceEndpoints.size();
		try {
			Integer currentIndexValue = INDEXES.put(service,
					(currentIndex.intValue() + 1) % numberOfServiceEndpoints);
			return currentIndexValue;
		} catch (ArithmeticException e) {
			return -1;
		}
	}

	protected void clearCache() {
		synchronized (CACHE) {
			CACHE.clear();
			ENDPOINTS.clear();
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
		Registry reg = service.getPort(Registry.class);
		return reg;
	}
}