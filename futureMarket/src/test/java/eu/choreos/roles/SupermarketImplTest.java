package eu.choreos.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.CarrefuturWS;
import eu.choreos.services.FutureMartWS;
import eu.choreos.services.PaoDoFuturoWS;
import eu.choreos.services.SMRegistryWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.Item;
import eu.choreos.vv.WSClient;
	
public class SupermarketImplTest {
	
	private final String FUTUREMART_WSDL = "http://localhost:8084/petals/services/futureMart?wsdl";
	private final String SM1 = "http://localhost:8084/petals/services/supermarket1?wsdl";
	private final String SM2 = "http://localhost:8084/petals/services/supermarket2?wsdl";
	private final String CARREFUTUR_WSDL = "http://localhost:8084/petals/services/carrefutur?wsdl";
	private final String PAO_DO_FUTURO_WSDL = "http://localhost:8084/petals/services/paoDoFuturo?wsdl";
	private final String REGISTRY_ENDPOINT = "http://localhost:1234/smregistry?wsdl";
	
	@BeforeClass
	public static void setUp(){
		RunWS.start(new FutureMartWS(), "futureMartWS");
		RunWS.start(new CarrefuturWS(), "carrefuturWS");
		RunWS.start(new PaoDoFuturoWS(), "paoDoFuturoWS");
		RunWS.start(new SMRegistryWS(), "smregistry");
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("futureMartWS");
		RunWS.stop("carrefuturWS");
		RunWS.stop("paoDoFuturoWS");		
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
	public void carrefuturShouldPlayTheSupermarketRole() throws Exception {
		WSClient futureMart = new WSClient(CARREFUTUR_WSDL);
		Item response = futureMart.request("searchForProduct", "milk");
		Item product = response.getChild("return");
		assertEquals("milk", product.getChild("name").getContent());
		assertEquals(new Double(3.95), product.getChild("price").getContentAsDouble());
	}
	
	@Test
	public void paoDoFuturoShouldPlayTheSupermarketRole() throws Exception {
		WSClient futureMart = new WSClient(PAO_DO_FUTURO_WSDL);
		Item response = futureMart.request("searchForProduct", "milk");
		Item product = response.getChild("return");
		assertEquals("milk", product.getChild("name").getContent());
		assertEquals(new Double(3.96), product.getChild("price").getContentAsDouble());
	}
	
	@Test
	public void sm1ShouldPlayTheSupermarketRole() throws Exception {
		WSClient futureMart = new WSClient(SM1);
		Item response = futureMart.request("searchForProduct", "milk");
		Item product = response.getChild("return");
		assertEquals("milk", product.getChild("name").getContent());
		assertEquals(new Double(3.95), product.getChild("price").getContentAsDouble());
	}
	
	@Test
	public void sm2ShouldPlayTheSupermarketRole() throws Exception {
		WSClient futureMart = new WSClient(SM2);
		Item response = futureMart.request("searchForProduct", "milk");
		Item product = response.getChild("return");
		assertEquals("milk", product.getChild("name").getContent());
		assertEquals(new Double(3.95), product.getChild("price").getContentAsDouble());
	}
	
	@Test
	public void shouldRegisterFuturMartfInRegistryWS() throws Exception {
		WSClient futureMart = new WSClient(FUTUREMART_WSDL);

		WSClient registry = new WSClient(REGISTRY_ENDPOINT);
		assertNull(registry.request("getList").getContent());
		futureMart.request("registerSupermarket", FUTUREMART_WSDL);
		Item response = registry.request("getList");
		
		assertEquals(FUTUREMART_WSDL, response.getChildAsList("return").get(0).getContent());
	}
	
	@Test
	public void shouldRegisterCarrefuturInRegistryWS() throws Exception {
		WSClient futureMart = new WSClient(CARREFUTUR_WSDL);

		WSClient registry = new WSClient(REGISTRY_ENDPOINT);
		assertNull(registry.request("getList").getContent());
		futureMart.request("registerSupermarket", CARREFUTUR_WSDL);
		Item response = registry.request("getList");
		
		assertEquals(CARREFUTUR_WSDL, response.getChildAsList("return").get(1).getContent());
	}
	
	@Test
	public void shouldRegisterPaoDoFuturoInRegistryWS() throws Exception {
		WSClient futureMart = new WSClient(PAO_DO_FUTURO_WSDL);

		WSClient registry = new WSClient(REGISTRY_ENDPOINT);
		assertNull(registry.request("getList").getContent());
		futureMart.request("registerSupermarket", PAO_DO_FUTURO_WSDL);
		Item response = registry.request("getList");
		
		assertEquals(PAO_DO_FUTURO_WSDL, response.getChildAsList("return").get(2).getContent());
	}
	
	@Test
	public void shouldRegisterSM1InRegistryWS() throws Exception {
		WSClient futureMart = new WSClient(SM1);

		WSClient registry = new WSClient(REGISTRY_ENDPOINT);
		assertNull(registry.request("getList").getContent());
		futureMart.request("registerSupermarket", SM1);
		Item response = registry.request("getList");
		
		assertEquals(SM1, response.getChildAsList("return").get(3).getContent());
	}
	
	@Test
	public void shouldRegisterSM2InRegistryWS() throws Exception {
		WSClient futureMart = new WSClient(SM2);

		WSClient registry = new WSClient(REGISTRY_ENDPOINT);
		assertNull(registry.request("getList").getContent());
		futureMart.request("registerSupermarket", SM2);
		Item response = registry.request("getList");
		
		assertEquals(SM2, response.getChildAsList("return").get(4).getContent());
	}

}
