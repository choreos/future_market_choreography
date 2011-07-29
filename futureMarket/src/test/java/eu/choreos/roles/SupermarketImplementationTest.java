package eu.choreos.roles;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.FutureMartWS;
import eu.choreos.services.SMRegistryWS;
import eu.choreos.utils.RunWS;

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.WSClient;

public class SupermarketImplementationTest {
	
	private final String FUTUREMART_WSDL = "http://localhost:8084/petals/services/futureMart?wsdl";
	private final String REGISTRY_ENDPOINT = "http://localhost:1234/smregistry?wsdl";
	
	@BeforeClass
	public static void setUp(){
		RunWS.start(new FutureMartWS(), "futureMartWS");
		RunWS.start(new SMRegistryWS(), "smregistry");
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("futureMartWS");
		RunWS.stop("smregistry");
	}

	@Test
	public void futureMartShouldPlayTheSupermarketRole() throws Exception {
		WSClient futureMart = new WSClient(FUTUREMART_WSDL);
		Item response = futureMart.request("searchForProduct", "milk");
		Item product = response.getChild("return");
		assertEquals("milk", product.getChild("name").getContent());
		assertEquals(new Double(4.79), product.getChild("price").getContentAsDouble());
	}
	
	@Test
	public void shouldRegisterItselfIntoRegistryWS() throws Exception {
		WSClient futureMart = new WSClient(FUTUREMART_WSDL);

		WSClient registry = new WSClient(REGISTRY_ENDPOINT);
		assertNull(registry.request("getList").getContent());
		futureMart.request("registerSupermarket", FUTUREMART_WSDL);
		Item response = registry.request("getList");
		
		assertEquals(FUTUREMART_WSDL, response.getChildAsList("return").get(0).getContent());
	}

}
