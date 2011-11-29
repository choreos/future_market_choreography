package eu.choreos.acceptance.workflow;

import static eu.choreos.utils.AcceptanceTestUtils.formatEndpoint;
import static eu.choreos.utils.AcceptanceTestUtils.getPersonalData;
import static eu.choreos.utils.AcceptanceTestUtils.registerSupermarkets;
import static eu.choreos.utils.AcceptanceTestUtils.removeSupermarkets;
import static eu.choreos.utils.ChoreographyAbstractor.getChoreography;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.abstractor.Choreography;
import eu.choreos.vv.abstractor.Service;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

public class FutureMarketWorkflowTest {

	private static Choreography futureMarket;
	private static Service smregistry;
	private static WSClient customerClient;
	private static String purchaseID;
	private static String shipperName;

	@BeforeClass
	public static void enactChoreography(){
		futureMarket = getChoreography();
		Service supermarket1 = futureMarket.getServicesForRole("supermarket").get(0);
		smregistry = supermarket1.getServicesForRole("supermarket").get(0);
		Service customer = futureMarket.getServicesForRole("customer").get(0);
		customerClient = customer.getWSClient();
		customerClient.setEndpoint(formatEndpoint(customer.getWSDL()));
	}
	
	
	@AfterClass
	public static void removeRegisteredSupermarkets() throws Exception {
		removeSupermarkets(smregistry);
	}

	@Test
	public void shouldRegisterSupermarkets() throws Exception {
		registerSupermarkets();
		WSClient smRegistryClient = smregistry.getWSClient();
		smRegistryClient.setEndpoint(smregistry.getWSDL());
		
		Item response = smRegistryClient.request("getList", (Item) null);
		List<Item> list = response.getChildAsList("return");
		validateEndpoints(list);
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
		
		
		Item response = customerClient.request("getPriceOfProductList", list);
		Double actualPrice = response.getChild("order").getChild("price").getContentAsDouble();
		
		Double minPrice = new Double(2.9);
		Double maxPrice = new Double(4.9);
		assertTrue(actualPrice.toString(), (actualPrice > minPrice && actualPrice < maxPrice));	
		
		purchaseID = response.getChild("order").getChild("id").getContent();
		assertTrue(Integer.parseInt(purchaseID) > 0);	
	}
	
	@Test
	public void shouldReturnTheShipperEndpointWhenProductsIsPurchased()
			throws Exception {

		Item request = new ItemImpl("purchase");
		Item orderID = new ItemImpl("id");
		orderID.setContent(purchaseID);
		request.addChild(orderID);

		Item personalData = getPersonalData();
		request.addChild(personalData);

		Item response = customerClient.request("purchase", request);
		shipperName = response.getChild("out").getContent();

		assertEquals("Shipper1", shipperName);
	}
	
	@Test
	public void shouldGetTheDeliveryDataOfAPurchase() throws Exception {
		Item request = new ItemImpl("getDeliveryData");
		Item shipper = new ItemImpl("shipper");
		shipper.setContent(shipperName);
		request.addChild(shipper);

		Item id = new ItemImpl("orderID");
		id.setContent(purchaseID);
		request.addChild(id);
		
		Item response = customerClient.request("getDeliveryData", request);
		
		assertTrue(response.getChild("delivery").getContent().contains("Sat Dec 24 20:15:00"));
	}

	
	
	private void validateEndpoints(List<Item> list) {
		List<Service> supermarkets = futureMarket.getServicesForRole("supermarket");
		
		for (Service service : supermarkets) {
			String wsdl = service.getWSDL();
			verifyIfEndpointExists(wsdl, list);
		}
	}

	private void verifyIfEndpointExists(String endpoint, List<Item> list) {
		for (Item item : list) {
			if(item.getContent().equals(endpoint))
				return;
		}
		fail("endpoint " + endpoint + " does not exists");
	}
}
