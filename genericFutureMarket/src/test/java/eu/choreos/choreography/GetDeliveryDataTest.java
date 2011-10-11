package eu.choreos.choreography;

import static eu.choreos.choreography.AcceptanceTestUtils.CUSTOMER;
import static eu.choreos.choreography.AcceptanceTestUtils.SMREGISTRY_ENDPOINT;
import static eu.choreos.choreography.AcceptanceTestUtils.purchaseProduct;
import static eu.choreos.choreography.AcceptanceTestUtils.registerSupermarkets;
import static eu.choreos.choreography.AcceptanceTestUtils.removeSupermarkets;
import static eu.choreos.choreography.AcceptanceTestUtils.requestIdOfSimpleOrder;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.WSClient;

public class GetDeliveryDataTest {
	
	private static String purchaseID;
	private static WSClient customer;
	private static String shipperName;

	@BeforeClass
	public static void setUpSupermarketsAndCustomerWSClientAndPurchaseProduc()
			throws Exception {
		registerSupermarkets();
		customer = new WSClient(CUSTOMER);
		purchaseID = requestIdOfSimpleOrder(customer);
		shipperName = purchaseProduct(customer, purchaseID);
	}
	
	@AfterClass
	public static void removeSupermarketsFromRegistry() throws Exception {
		WSClient smregistry = new WSClient(SMREGISTRY_ENDPOINT);
		removeSupermarkets(smregistry);
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
		
		Item response = customer.request("getDeliveryData", request);
		
		assertEquals("Sat Dec 24 20:15:00 BRST 2011", response.getChild("delivery").getContent());
	}

}
