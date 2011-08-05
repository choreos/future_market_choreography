package eu.choreos.roles;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.ItemImpl;
import br.usp.ime.choreos.vv.WSClient;
import eu.choreos.services.CarrefuturWS;
import eu.choreos.services.CustomerWS;
import eu.choreos.services.FutureMartWS;
import eu.choreos.services.PaoDoFuturoWS;
import eu.choreos.services.SMRegistryWS;
import eu.choreos.utils.RunWS;


public class SupermarketCustomerImplTest {
	
	private final String CUSTOMER = "http://localhost:8084/petals/services/customer?wsdl";
	private final static String FUTUREMART_WSDL = "http://localhost:8084/petals/services/futureMart?wsdl";
	private final static String CARREFUTUR_WSDL = "http://localhost:8084/petals/services/carrefutur?wsdl";
	private final static String PAO_DO_FUTURO_WSDL = "http://localhost:8084/petals/services/paoDoFuturo?wsdl";
	
	@BeforeClass
	public static void setUp() throws Exception{
		RunWS.start(new FutureMartWS(), "futureMartWS");
		RunWS.start(new CarrefuturWS(), "carrefuturWS");
		RunWS.start(new PaoDoFuturoWS(), "paoDoFuturoWS");
		RunWS.start(new SMRegistryWS(), "smregistry");
		RunWS.start(new CustomerWS(), "customerWS");
		
		registerSupermarkets(FUTUREMART_WSDL, CARREFUTUR_WSDL, PAO_DO_FUTURO_WSDL);
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("futureMartWS");
		RunWS.stop("carrefuturWS");
		RunWS.stop("paoDoFuturoWS");		
		RunWS.stop("smregistry");
		RunWS.stop("customerWS");
	}
	
	private static void registerSupermarkets(String... endpoints) throws Exception{
		for(String endpoint : endpoints){
			WSClient supermarket = new WSClient(endpoint);
			supermarket.request("registerSupermarket", endpoint);
		}
	}
	
	@Test
	public void shouldReturnTheCorrectPriceOfAListWithOneItem() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		Item item1 = new ItemImpl("item");
		item1.setContent("milk");
		list.addChild(item1);
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals(new Double(3.95), response.getChild("price").getContentAsDouble());	
	}
	
	
	@Test
	public void shouldReturnTheCorrectPriceOfAListWithTwoItems() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("milk");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("cereal");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals(new Double(3.95 + 21.00), response.getChild("price").getContentAsDouble());	
	}


}
