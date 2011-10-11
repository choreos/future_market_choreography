package eu.choreos.choreography;

import static eu.choreos.choreography.AcceptanceTestUtils.getPersonalData;
import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.WSClient;

public class AcceptanceTestUtils {

	final static public int NUMBER_OF_SUPERMARKETS = 2;
	final static private String IP = "localhost";
	final static public String SM_ENDPOINT_BASE = "http://" + IP + ":8084/petals/services/supermarket";
	final static public String SMREGISTRY_ENDPOINT = "http://" + IP + ":1234/smregistry?wsdl";
	final static public String CUSTOMER = "http://" + IP + ":8084/petals/services/customer?wsdl";



	public static void removeSupermarkets(WSClient smregistry) throws Exception {
		for (int i = 1; i <= NUMBER_OF_SUPERMARKETS; i++) {
			String endpoint = generateEndpoint(i);
			smregistry.request("removeSupermarket", endpoint);
		}
	}

	public static void registerSupermarkets() throws Exception {
		for (int i = 1; i <= NUMBER_OF_SUPERMARKETS; i++) {
			String endpoint = generateEndpoint(i);
			WSClient supermarket = new WSClient(endpoint);
			supermarket.request("registerSupermarket", endpoint);
		}
	}

	public static String generateEndpoint(int i) {
		return SM_ENDPOINT_BASE + Integer.toString(i) + "?wsdl";
	}
	
	public static Item getPersonalData() {
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
	
	public static String requestIdOfSimpleOrder(WSClient customer) throws Exception {
		Item list = new ItemImpl("getPriceOfProductListRequest");
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);

		Item response = customer.request("getPriceOfProductList", list);
		return response.getChild("order").getChild("id")
				.getContent();
	}
	

	public static String purchaseProduct(WSClient customer, String purchaseID) throws Exception {
		Item request = new ItemImpl("purchase");
		Item orderID = new ItemImpl("id");
		orderID.setContent(purchaseID);
		request.addChild(orderID);

		Item personalData = getPersonalData();
		request.addChild(personalData);

		Item response = customer.request("purchase", request);
		return response.getChild("out").getContent();
	}
}
