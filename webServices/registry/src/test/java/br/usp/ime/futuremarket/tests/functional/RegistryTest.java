package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Registry;
import br.usp.ime.futuremarket.RegistryUtils;

public class RegistryTest {
	private static final String ROLE = "TestRole";
	private static final String NAME = "Walmart";
	private static final String ENDPOINT = "http://www.walmart.com";
	private static final String PROP_FILE = "registry.properties";
	private static final String PROP_KEY = "registry.wsdl";

	/*
	 * The constants below are available in FutureMarket project that depends on
	 * this (Registry) project. Since we don't want circular dependencies, we
	 * won't use them and we'll declare our own.
	 */
	private static final String NAMESPACE = "http://futuremarket.ime.usp.br";
	private static final String LPART = "RegistryImplService";

	private static Registry registry;
	private Map<String, List<String>> wsdls;

	@BeforeClass
	public static void initRegistryClient() throws IOException {
		final String registryWsdl = getRegistryWsdl();
		registry = getRegistryClient(registryWsdl);
	}

	private static Registry getRegistryClient(final String registryWsdl)
			throws MalformedURLException {
		final URL url = new URL(registryWsdl);
		final QName qname = new QName(NAMESPACE + "/registry", LPART);
		final Service service = Service.create(url, qname);

		return service.getPort(Registry.class);
	}

	private static String getRegistryWsdl() throws IOException {
		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		final InputStream file = loader.getResourceAsStream(PROP_FILE);
		final Properties properties = new Properties();

		properties.load(file);

		return properties.getProperty(PROP_KEY);
	}

	// @Test Now the supermarkets are added in enactment time
	public void shouldBeginEmpty() {
		assertTrue("Opps, got " +registry.getServices(ROLE).size(), registry.getServices(ROLE).isEmpty());
	}

	//@Test
	public void shouldAddASupermarket() {
		registry.setInvocationAddress(ROLE, NAME, Arrays.asList(ENDPOINT));

		wsdls = RegistryUtils.toMap(registry.getServices(ROLE));

		assertEquals(1, wsdls.size());
		assertEquals(ENDPOINT, wsdls.values().iterator().next().get(0));

		final List<String> wsdl = registry.getInstances(NAME);

		assertEquals(ENDPOINT, wsdl.get(0));
	}

	@Test
	public void shouldAddTwoSupermarkets() {
		registry.setInvocationAddress(ROLE, NAME + "2", Arrays.asList(ENDPOINT, ENDPOINT + "2"));
		registry.setInvocationAddress(ROLE, NAME, Arrays.asList(ENDPOINT + "2"));

		wsdls = RegistryUtils.toMap(registry.getServices(ROLE));

		assertEquals(2, wsdls.size());
		assertEquals(ENDPOINT + "2", wsdls.values().iterator().next().get(1));

		final List<String> wsdl = registry.getInstances(NAME + "2");
		assertEquals(ENDPOINT + 2, wsdl.get(1));
	}
}