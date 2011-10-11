package eu.choreos.choreography;

import static eu.choreos.choreography.AcceptanceTestUtils.CUSTOMER;
import static eu.choreos.choreography.AcceptanceTestUtils.SMREGISTRY_ENDPOINT;
import static eu.choreos.choreography.AcceptanceTestUtils.getPersonalData;
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

public class PurchaseTest {

	private static WSClient customer;
	private static String purchaseID;

	@BeforeClass
	public static void setUpSupermarketsAndCustomerWSClientAndRequestProductList()
			throws Exception {
		registerSupermarkets();
		customer = new WSClient(CUSTOMER);
		purchaseID = requestIdOfSimpleOrder(customer);
		
	}
	
	@AfterClass
	public static void removeSupermarketsFromRegistry() throws Exception {
		WSClient smregistry = new WSClient(SMREGISTRY_ENDPOINT);
		removeSupermarkets(smregistry);
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

		Item response = customer.request("purchase", request);

		assertEquals("Shipper1", response.getChild("out").getContent());
	}

}
