package eu.choreos.choreography;

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
}
