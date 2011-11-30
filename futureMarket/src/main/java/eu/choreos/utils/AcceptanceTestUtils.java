package eu.choreos.utils;

import static eu.choreos.utils.ChoreographyAbstractor.getChoreography;

import java.util.List;

import eu.choreos.vv.abstractor.Choreography;
import eu.choreos.vv.abstractor.Service;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

public class AcceptanceTestUtils {

	final static private String IP = "localhost";
	final static public String PREFIX = "http://" + IP + ":8084/petals/services/";
	
	private static Choreography futureMarket = getChoreography();
	private static List<Service> supermarkets = futureMarket.getServicesForRole("supermarket");

	// Administrative operations that must be performed
	// ------------------------------------------------------------------------------------------------------------------------------------------
	public static void removeSupermarkets(Service smregistry) throws Exception {
		WSClient smregistryClient = new WSClient(smregistry.getWSDL());
		smregistryClient.setEndpoint(formatEndpoint(smregistry.getWSDL()));

		for (Service service : supermarkets) 
			smregistryClient.request("removeSupermarket", service.getWSDL());
	}

	public static void registerSupermarkets() throws Exception {
		for (Service service : supermarkets) {
			String wsdl = service.getWSDL();
			WSClient serviceClient = new WSClient(wsdl);
			serviceClient.setEndpoint(formatEndpoint(wsdl));
			serviceClient.request("registerSupermarket", wsdl);
		}
	}

	public static String requestIdOfSimpleOrder(Service customer) throws Exception {
		WSClient customerClient = new WSClient(customer.getWSDL());
		
		Item list = new ItemImpl("getPriceOfProductListRequest");
		Item item1 = new ItemImpl("item");
		item1.setContent("product1");
		list.addChild(item1);
		
		customerClient.setEndpoint(formatEndpoint(customer.getWSDL()));
		Item response = customerClient.request("getPriceOfProductList", list);
		return response.getChild("order").getChild("id")
						.getContent();
	}
	
	public static String purchaseProduct(Service customer, String purchaseID) throws Exception {
		WSClient customerClient = new WSClient(customer.getWSDL());

		Item request = new ItemImpl("purchase");
		Item orderID = new ItemImpl("id");
		orderID.setContent(purchaseID);
		request.addChild(orderID);
		
		Item personalData = getPersonalData();
		request.addChild(personalData);
		
		customerClient.setEndpoint(formatEndpoint(customer.getWSDL()));
		Item response = customerClient.request("purchase", request);
		return response.getChild("out").getContent();
	}
	
	public static String formatEndpoint(String wsdl) {
		return wsdl.substring(0, wsdl.length() - 5);
	}

	
	// Test Data
	// ------------------------------------------------------------------------------------------------------------------------------------------
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
	
}
