package eu.choreos.services.tests.functional;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.RegistryWS;

public class RegistryWSTest {
    public final static String ROLE = "Supermarket";
    public final static String ENDPOINT = "http://www.walmart.com";
    static final ClassLoader loader = RegistryWSTest.class.getClassLoader();
    private static RegistryWS registry;

    @BeforeClass
    public static void oneTimeSetUp() throws FileNotFoundException, IOException {
        final String wsdl = getWsdl();
        URL url = new URL(wsdl);
        QName qname = new QName("http://registry.choreos.eu", "RegistryWSImplService");
        Service service = Service.create(url, qname);
        registry = service.getPort(RegistryWS.class);
    }

    private static String getWsdl() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(loader.getResourceAsStream("config.properties"));
        return properties.getProperty("registry.wsdl");
    }

    @Test
    public void shouldBeginEmpty() {
        List<String> wsdls = registry.getList(ROLE);
        assertEquals(0, wsdls.size());
    }

    @Test
    public void shouldAddASupermarket() {
        registry.add(ROLE, ENDPOINT);

        List<String> wsdls = registry.getList(ROLE);

        assertEquals(1, wsdls.size());
        assertEquals(ENDPOINT, wsdls.get(0));
    }

    @Test
    public void shouldRemoveSupermarket() {
        registry.remove(ROLE, ENDPOINT);
        List<String> wsdls = registry.getList(ROLE);
        assertEquals(0, wsdls.size());
    }
    
}