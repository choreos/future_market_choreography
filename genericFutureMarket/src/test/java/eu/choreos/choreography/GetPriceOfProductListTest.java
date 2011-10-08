package eu.choreos.choreography;

import static eu.choreos.choreography.AcceptanceTestUtils.CUSTOMER;
import static eu.choreos.choreography.AcceptanceTestUtils.SMREGISTRY_ENDPOINT;
import static eu.choreos.choreography.AcceptanceTestUtils.registerSupermarkets;
import static eu.choreos.choreography.AcceptanceTestUtils.removeSupermarkets;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.WSClient;

public class GetPriceOfProductListTest {

	private static WSClient customer;

	@BeforeClass
	public static void setUpSupermarketsAndCustomerWSClient() throws Exception {
		registerSupermarkets();
		customer = new WSClient(CUSTOMER);
	}

	@AfterClass
	public static void removeRegisteredSupermarkets() throws Exception {
		WSClient smregistry = new WSClient(SMREGISTRY_ENDPOINT);
		removeSupermarkets(smregistry);
	}

	@Test
	public void shouldReturnTheLowerPriceOfAProduct() throws Exception {
		Item list = new ItemImpl("getPriceOfProductListRequest");
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);

		Item response = customer.request("getPriceOfProductList", list);
		Double actualPrice = response.getChild("order").getChild("price")
				.getContentAsDouble();

		assertTrue(actualPrice.toString(),
				(actualPrice > 0.9 && actualPrice < 2.0));
	}
	
	@Test
	public void shouldReturnTheCorrectPriceOfAListWithTwoItems() throws Exception {
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		Item item2 = new ItemImpl("item");
		item2.setContent("product2");
		list.addChild(item2);
		
		
		Item response = customer.request("getPriceOfProductList", list);
		Double actualPrice = response.getChild("order").getChild("price").getContentAsDouble();
		
		Double minPrice = new Double(2.9);
		Double maxPrice = new Double(4.9);
		
		assertTrue(actualPrice.toString(), (actualPrice > minPrice && actualPrice < maxPrice));	
	}
	
	@Test
	public void shouldReturnAnOrderID() throws Exception {
		Item list = new ItemImpl("getPriceOfProductListRequest");
		
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		Item response = customer.request("getPriceOfProductList", list);
		
		String id = response.getChild("order").getChild("id").getContent();
		
		assertTrue(Integer.parseInt(id) > 0);	
	}

}
