package eu.choreos.roles;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.CarrefuturWS;
import eu.choreos.services.CustomerWS;
import eu.choreos.services.FutureMartWS;
import eu.choreos.services.PaoDoFuturoWS;
import eu.choreos.services.SMRegistryWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.WSClient;


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
		
		registerSupermarkets(FUTUREMART_WSDL, CARREFUTUR_WSDL, PAO_DO_FUTURO_WSDL);
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
		RunWS.stop("futureMartWS");
		RunWS.stop("carrefuturWS");
		RunWS.stop("paoDoFuturoWS");		
		RunWS.stop("smregistry");
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
		
		assertEquals(new Double(3.95), response.getChild("order").getChild("price").getContentAsDouble());	
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
		
		assertEquals(new Double(3.95 + 21.00), response.getChild("order").getChild("price").getContentAsDouble());	
	}
	
	@Test
	public void shouldReturnAnOrderID() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("milk");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("cereal");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals("1", response.getChild("order").getChild("id").getContent());	
	}
	
	@Test
	public void shouldReturnAnOrderIDAndTheLowestPriceList() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("milk");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("cereal");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals("1", response.getChild("order").getChild("id").getContent());
		assertEquals(new Double(3.95 + 21.00), response.getChild("order").getChild("price").getContentAsDouble());
	}


}
