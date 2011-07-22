package eu.choreos.roles;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.utils.RunWS;

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.WSClient;

public class SupermarketImplementationTest {
	
	private final String FUTUREMART_WSDL = "http://localhost:8084/petals/services/futureMart?wsdl";
	
	@BeforeClass
	public static void setUp(){
		RunWS.startFutureMartWS();
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stopFutureMartWS();
	}

	@Test
	public void futureMartShouldPlayTheSupermarketRole() throws Exception {
		WSClient futureMartWS = new WSClient(FUTUREMART_WSDL);
		Item response = futureMartWS.request("searchForProduct", "milk");
		Item product = response.getChild("return");
		assertEquals("milk", product.getChild("name").getContent());
		assertEquals(new Double(4.79), product.getChild("price").getContentAsDouble());
	}
	
	@Test
	public void shouldRegisterItselfIntoRegistryWS() throws Exception {
		RunWS.startSMRegistryWS();
		WSClient futureMartWS = new WSClient(FUTUREMART_WSDL);
		WSClient registry = new WSClient(RunWS.REGISTRY_ENDPOINT + "?wsdl");
		assertNull(registry.request("getList").getContent());
		futureMartWS.request("registerSupermarket", FUTUREMART_WSDL);
		registry.request("getList");
		
		
		RunWS.stopSMRegistryWS();
	}

}
