package eu.choreos.services;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.utils.RunWS;
import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.WSClient;


public class CustomerWSTest {
	
	private final String FUTUREMART_WSDL = "http://localhost:8084/petals/services/futureMart?wsdl";
	private final String CARREFUTUR_WSDL = "http://localhost:8084/petals/services/carrefutur?wsdl";
	private final String CUSTOMER_WS_WSDL = "http://localhost:1234/customerWS?wsdl";

	
	@Before
	public void setUp(){
		RunWS.start(new CustomerWS(), "customerWS");
		RunWS.start(new FutureMartWS(), "futureMartWS");
		RunWS.start(new CarrefuturWS(), "carrefuturWS");
	}
	
	@After
	public void tearDown(){
		RunWS.stop("customerWS");
		RunWS.stop("futureMartWS");
		RunWS.stop("carrefuturWS");
	}
	
	@Test
	public void shouldGetLowestPriceOfAListWithOneItemAndOneSupermarket() throws Exception {
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(FUTUREMART_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("milk");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		assertEquals(new Double(4.79), response.getChild("return").getChild("price").getContentAsDouble());		
	}
	
	@Test
	public void shouldGetLowestPriceOfAListWithOneItemAndTwoSupermarkets() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(FUTUREMART_WSDL, CARREFUTUR_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("milk");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		assertEquals(new Double(3.95), response.getChild("return").getChild("price").getContentAsDouble());		

	}
	
	@Test
	public void shouldGetLowestPriceOfAListWithTwoItemsAndTwoSupermarkets() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(FUTUREMART_WSDL, CARREFUTUR_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("milk", "cereal");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		double listPrice = 3.95 + 21.00;
		assertEquals(new Double(listPrice), response.getChild("return").getChild("price").getContentAsDouble());		

	}
	
	@Test
	public void shouldRetrieveAnIDOrder() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(FUTUREMART_WSDL, CARREFUTUR_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("milk", "cereal");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		double listPrice = 3.95 + 21.00;
		
		assertEquals("1", response.getChild("return").getChild("id").getContent());
		assertEquals(new Double(listPrice), response.getChild("return").getChild("price").getContentAsDouble());
	}
	
	@Test
	public void shouldRetrieveAnIDOrderAndTheListPrice() throws Exception{
		WSClient customerWS = new WSClient(CUSTOMER_WS_WSDL);
		
		Item request = generateSupermarketsList(FUTUREMART_WSDL, CARREFUTUR_WSDL);		
		customerWS.request("setSupermarketsList", request);
		
		request = generateProductsList("milk", "cereal");
		customerWS.request("setProductsList", request);
		
		Item response = customerWS.request("getLowestPriceForList");
		
		assertEquals("1", response.getChild("return").getChild("id").getContent());		
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
