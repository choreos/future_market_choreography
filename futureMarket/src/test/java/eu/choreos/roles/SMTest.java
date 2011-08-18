package eu.choreos.roles;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.CustomerWS;
import eu.choreos.services.SMRegistryWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.WSClient;

public class SMTest {
	
	private final String CUSTOMER = "http://localhost:8084/petals/services/customer?wsdl";
	private final static String SM1 = "http://localhost:8084/petals/services/supermarket1?wsdl";
	private final static String SM2 = "http://localhost:8084/petals/services/supermarket2?wsdl";

	@BeforeClass
	public static void setUp() throws Exception{
		// the script script must be executed before this test to deploy at least 2 sm's services
		RunWS.start(new SMRegistryWS(), "smregistry");
		registerSupermarkets(SM1, SM2);
	}
	
	private static void registerSupermarkets(String... endpoints) throws Exception{
		for(String endpoint : endpoints){
			WSClient supermarket = new WSClient(endpoint);
			supermarket.request("registerSupermarket", endpoint);
		}
	}
	
	@Before
	public void startCustomerWS(){
		RunWS.start(new CustomerWS(), "customerWS");
	}
	
	@After
	public void stopCustomerWS(){
		RunWS.stop("customerWS");
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("smregistry");
	}
	
	@Test
	public void shouldReturnTheCorrectPriceOfAListWithOneItem() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		Item item1 = new ItemImpl("item");
		item1.setContent("milk");
		list.addChild(item1);
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals(new Double(3.95), response.getChild("order").getChild("price").getContentAsDouble());	
	}
	
	
	
	

}
