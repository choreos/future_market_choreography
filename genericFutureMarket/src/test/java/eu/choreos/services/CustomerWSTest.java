package eu.choreos.services;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.utils.RunWS;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;


public class CustomerWSTest {
	
	private final String SM1_WSDL = "http://localhost:8084/petals/services/supermarket1?wsdl";
	private final String SM2_WSDL = "http://localhost:8084/petals/services/supermarket2?wsdl";
	private final String CUSTOMER_WS_WSDL = "http://localhost:1234/customerWS?wsdl";

	
	@Before
	public void setUp(){
		RunWS.start(new CustomerWS(), "customerWS");
		RunWS.start(new ShipperWS(), "shipperWS");
	}
	
	@After
	public void tearDown(){
		RunWS.stop("customerWS");
		RunWS.stop("shipperWS");
	}
	
	@Test
	public void shouldGetLowestPriceOfAListWithOneItemAndOneSupermarket() throws Exception {
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(SM1_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("product1");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		assertEquals(new Double(1.5), response.getChild("return").getChild("price").getContentAsDouble());		
	}
	
	@Test
	public void shouldGetLowestPriceOfAListWithOneItemAndTwoSupermarkets() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(SM1_WSDL, SM2_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("product1");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		assertEquals(new Double(1.5), response.getChild("return").getChild("price").getContentAsDouble());		

	}
	
	@Test
	public void shouldGetLowestPriceOfAListWithTwoItemsAndTwoSupermarkets() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(SM1_WSDL, SM2_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("product1", "product2");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		double listPrice = 1.0 + 2.5;
		assertEquals(new Double(listPrice), response.getChild("return").getChild("price").getContentAsDouble());		

	}
	
	@Test
	public void shouldRetrieveAnIDOrder() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(SM1_WSDL, SM2_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("product1", "product2");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		double listPrice = 1.0 + 2.5;
		
		assertEquals("1", response.getChild("return").getChild("id").getContent());
		assertEquals(new Double(listPrice), response.getChild("return").getChild("price").getContentAsDouble());
	}
	
	@Test
	public void shouldRetrieveAnIDOrderAndTheListPrice() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(SM1_WSDL, SM2_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("product1", "product2");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		assertEquals("1", response.getChild("return").getChild("id").getContent());		
	}
	
	@Test
	public void shouldRetrieveAnIDOrderAndThenPurchase() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(SM1_WSDL, SM2_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("product1", "product2");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		String orderID = response.getChild("return").getChild("id").getContent();
		
		response = customerWS.request("makePurchase", orderID, "aName", "anAddress", "999999");
		
		assertEquals("Shipper1", response.getChild("return").getContent());		
	}
	
	private Item generateSupermarketsList(String... endpoints){
		Item request = new ItemImpl("setSupermarketsList");

		for(String endpoint : endpoints){
			Item entry = new ItemImpl("endpoint");
			entry.setContent(endpoint);
			request.addChild(entry);
		}
		
		return request;
	}
	
	private Item generateProductsList(String... products){
		Item request = new ItemImpl("setProductsList");

		for(String product : products){
			Item entry = new ItemImpl("item");
			entry.setContent(product);
			request.addChild(entry);
		}
		
		return request;
	}

}
