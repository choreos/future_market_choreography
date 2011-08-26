package eu.choreos.roles;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import eu.choreos.services.CarrefuturWS;
import eu.choreos.services.CustomerWS;
import eu.choreos.services.FutureMartWS;
import eu.choreos.services.PaoDoFuturoWS;
import eu.choreos.services.SMRegistryWS;
import eu.choreos.services.ShipperWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;


public class SMCustomerImplTest {
	
	private final String CUSTOMER = "http://localhost:8084/petals/services/customer?wsdl";
	private final static String SM1 = "http://localhost:8084/petals/services/supermarket1?wsdl";
	private final static String SM2 = "http://localhost:8084/petals/services/supermarket2?wsdl";
	
	@BeforeClass
	public static void setUp() throws Exception{
		RunWS.start(new SMRegistryWS(), "smregistry");
		RunWS.start(new ShipperWS(), "shipperWS");
		
		registerSupermarkets(SM1, SM2);
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
		RunWS.stop("shipperWS");
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
		item1.setContent("product1");
		list.addChild(item1);
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals(new Double(1.5), response.getChild("order").getChild("price").getContentAsDouble());	
	}
	
	
	@Test
	public void shouldReturnTheCorrectPriceOfAListWithTwoItems() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("product2");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals(new Double(1.0 + 2.5), response.getChild("order").getChild("price").getContentAsDouble());	
	}
	
	@Test
	public void shouldReturnAnOrderID() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("product2");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals("1", response.getChild("order").getChild("id").getContent());	
	}
	
	@Test
	public void shouldReturnAnOrderIDAndTheLowestPriceList() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("product2");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		
		assertEquals("1", response.getChild("order").getChild("id").getContent());
		assertEquals(new Double(1.0 + 2.5), response.getChild("order").getChild("price").getContentAsDouble());
	}
	
	@Test
	public void shouldReceiveAConfirmationMessageAfterPurchasing() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("product2");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		String purchaseID = response.getChild("order").getChild("id").getContent();
		
		Item request = new ItemImpl("purchase");
		Item orderID = new ItemImpl("id");
		orderID.setContent(purchaseID);
		request.addChild(orderID);
		
		Item personalData = getPersonalData();
		request.addChild(personalData);
		
		response = customer.request("purchase", request);
		
		assertEquals("Shipper1", response.getChild("out").getContent());
	}
	
	@Test
	public void shouldRetriveDeliveryDataAfterPurchasing() throws Exception {
		WSClient customer = new WSClient(CUSTOMER);
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("product2");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		String purchaseID = response.getChild("order").getChild("id").getContent();
		
		Item request = new ItemImpl("purchase");
		Item orderID = new ItemImpl("id");
		orderID.setContent(purchaseID);
		request.addChild(orderID);
		
		Item personalData = getPersonalData();
		request.addChild(personalData);
		
		response = customer.request("purchase", request);
		
		String shipperName = response.getChild("out").getContent();
		
		request = new ItemImpl("getDeliveryData");
		Item shipper = new ItemImpl("shipper");
		shipper.setContent(shipperName);
		request.addChild(shipper);

		Item id = new ItemImpl("orderID");
		id.setContent(purchaseID);
		request.addChild(id);
		
		response = customer.request("getDeliveryData", request);
		
		assertEquals("Sat Dec 24 20:15:00 BRST 2011", response.getChild("delivery").getContent());
	}
	
	private Item getPersonalData() {
		Item personalData = new ItemImpl("account");
		
		Item name = new ItemImpl("name");
		name.setContent("Audrey H. Bowers");
		personalData.addChild(name);
		
		Item address = new ItemImpl("address");
		address.setContent("2421 West Drive");
		personalData.addChild(address);
		
		Item zipCode = new ItemImpl("zipcode");
		zipCode.setContent("60606");
		personalData.addChild(zipCode);
		return personalData;
	}


}
