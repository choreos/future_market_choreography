package eu.choreos.services;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.xmlbeans.XmlException;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class SMRegistryWSTest {
    static WSClient registry;
    public final static String ROLE = "Supermarket";
    public final static String ENDPOINT = "http://www.walmart.com";
    static final ClassLoader loader = SMRegistryWSTest.class.getClassLoader();

    @BeforeClass
    public static void oneTimeSetUp() throws FileNotFoundException, IOException, WSDLException,
            XmlException, FrameworkException {
        final String wsdl = getWsdl();
        registry = new WSClient(wsdl);
    }

    private static String getWsdl() throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(loader.getResourceAsStream("config.properties"));
        return properties.getProperty("registry.wsdl");
    }

    @Test
    public void shouldBeginEmpty() throws InvalidOperationNameException, FrameworkException {
        Item response = registry.request("getList", ROLE);
        assertEquals(new Integer(0), response.getChildrenCount());
    }

    @Test
    public void shouldAddASupermarket() throws InvalidOperationNameException, FrameworkException,
            NoSuchFieldException {
        registry.request("add", ROLE, ENDPOINT);

        Item response = registry.request("getList", ROLE);

        assertEquals(new Integer(1), response.getChildrenCount());
        assertEquals(ENDPOINT, response.getChild("return").getContent());
    }

    @Test
    public void shouldRemoveSupermarket() throws InvalidOperationNameException, FrameworkException {
        registry.request("remove", ROLE, ENDPOINT);
        Item response = registry.request("getList", ROLE);
        assertEquals(new Integer(0), response.getChildrenCount());
    }
}