package br.usp.ime.futuremarket.choreography.tests.functional;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.choreography.Registry;


public class RegistryTest {
    public final static String ROLE = "Supermarket";
    public final static String NAME = "WALMART";
    public final static String ENDPOINT = "http://www.walmart.com";
    static final ClassLoader loader = RegistryTest.class.getClassLoader();
    private static Registry registry;

    @BeforeClass
    public static void oneTimeSetUp() throws FileNotFoundException, IOException {
        final String wsdl = "http://127.0.0.1:8080/registry/registry?wsdl";
        URL url = new URL(wsdl);
        QName qname = new QName("http://futuremarket.ime.usp.br", "RegistryImplService");
        Service service = Service.create(url, qname);
        registry = service.getPort(Registry.class);
    }

    @Test
    public void shouldBeginEmpty() {
        List<String> wsdls = registry.getList(ROLE);
        assertEquals(0, wsdls.size());
    }

    @Test
    public void shouldAddASupermarket() {
        registry.add(ROLE, NAME, ENDPOINT);

        List<String> wsdls = registry.getList(ROLE);

        assertEquals(1, wsdls.size());
        assertEquals(ENDPOINT, wsdls.get(0));
        
        String wsdl = registry.getServiceEndpoint(NAME);
        assertEquals(ENDPOINT, wsdl);
    }

    @Test
    public void shouldRemoveSupermarket() {
        registry.remove(ROLE, NAME);
        List<String> wsdls = registry.getList(ROLE);
        assertEquals(0, wsdls.size());
        
        String wsdl = registry.getServiceEndpoint(NAME);
        assertEquals("", wsdl);
    }
}