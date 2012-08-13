package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Registry;

public class RegistryTest {
    private static final String ROLE = "Supermarket";
    private static final String NAME = "WALMART";
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
    private List<String> wsdls;

    @BeforeClass
    public static void initRegistryClient() throws IOException {
        final String registryWsdl = getRegistryWsdl();
        registry = getRegistryClient(registryWsdl);
    }

    private static Registry getRegistryClient(final String registryWsdl)
            throws MalformedURLException {
        final URL url = new URL(registryWsdl);
        final QName qname = new QName(NAMESPACE, LPART);
        final Service service = Service.create(url, qname);

        return service.getPort(Registry.class);
    }

    private static String getRegistryWsdl() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream file = loader.getResourceAsStream(PROP_FILE);
        final Properties properties = new Properties();

        properties.load(file);

        return properties.getProperty(PROP_KEY);
    }

    @Test
    public void shouldBeginEmpty() {
        wsdls = registry.getServices(ROLE);
        assertEquals(0, wsdls.size());
    }

    @Test
    public void shouldAddASupermarket() {
        registry.addService(ROLE, NAME, ENDPOINT);

        wsdls = registry.getServices(ROLE);

        assertEquals(1, wsdls.size());
        assertEquals(ENDPOINT, wsdls.get(0));

        final String wsdl = registry.getServiceByName(NAME);
        assertEquals(ENDPOINT, wsdl);
    }

    @Test
    public void shouldAddASecondSupermarket() {
        registry.addService(ROLE, NAME + "2", ENDPOINT + "2");

        wsdls = registry.getServices(ROLE);

        assertEquals(2, wsdls.size());
        assertEquals(ENDPOINT + "2", wsdls.get(1));

        final String wsdl = registry.getServiceByName(NAME + "2");
        assertEquals(ENDPOINT + 2, wsdl);
    }

    @Test
    public void shouldRemoveAllSupermarkets() {
        registry.removeService(ROLE, NAME);
        registry.removeService(ROLE, NAME + "2");

        wsdls = registry.getServices(ROLE);
        assertEquals(0, wsdls.size());

        final String wsdl = registry.getServiceByName(NAME);
        assertEquals("", wsdl);
    }
}