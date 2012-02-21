package eu.choreos.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import eu.choreos.CustomerInfo;
import eu.choreos.DeliveryInfo;
import eu.choreos.ProductPrice;
import eu.choreos.PurchaseInfo;
import eu.choreos.models.LowestPrice;

@WebService(
		targetNamespace = "http://customer.choreos.eu",
		endpointInterface = "eu.choreos.services.CustomerWS",
		serviceName = "CustomerWSImplService"
		)
public class CustomerWSImpl implements CustomerWS {

	static RegistryWS registryWS;
	final ClassLoader loader = CustomerWSImpl.class.getClassLoader();
	private HashMap<String, HashMap<String, Set<String>>> customerProductLists; //<listID, <supermarket, <product>>>
	private long currentList = 1L;

	public CustomerWSImpl() throws FileNotFoundException, IOException {
		registryWS = WsdlInfo.getPort(getRegistryWsdl(), RegistryWS.class);
		customerProductLists = new HashMap<String, HashMap<String, Set<String>>>();
	}

	private String getMyWsdl() throws MalformedURLException, UnknownHostException {
		final String hostName = getMyHostName();
		return "http://" + hostName + ":8080/customer/customer?wsdl";
	}

	private String getMyHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getCanonicalHostName();
	}

	private String getRegistryWsdl() throws FileNotFoundException, IOException {
		return getWsdl("registry.wsdl");
	}

	private String getWsdl(String name) throws FileNotFoundException,
	IOException {
		Properties properties = new Properties();
		properties.load(loader.getResourceAsStream("config.properties"));
		return properties.getProperty(name);
	}

	@WebMethod
	public LowestPrice getLowestPriceForList(String[] products) {
		try {
			HashMap<HashMap<String, Double>, String> supermarketsProductList = new HashMap<HashMap<String, Double>, String>();

			List<String> endpoints = registryWS.getList("Supermarket");
			//gets prices from supermarkets	
			for (String endpoint : endpoints) {
				SupermarketWS supermarketWS = WsdlInfo.getPort(endpoint, SupermarketWS.class);
				ProductPrice[] productPrices = supermarketWS.getPrices(products);

				HashMap<String, Double> productsMap = new HashMap<String, Double>();
				for (ProductPrice p: productPrices) {
					productsMap.put(p.getProduct(), p.getPrice());
				}
				supermarketsProductList.put(productsMap, endpoint);
			}

			String listId = "" + getListId();
			customerProductLists.put(listId, new HashMap<String, Set<String>>());
			Double listPrice = 0d;
			//finds lowest prices
			for (String product : products) {
				String endpoint = "";
				Double lowestPrice = Double.MAX_VALUE;
				for (HashMap<String, Double> productsHash : supermarketsProductList.keySet()) {
					Double price = productsHash.get(product);
					if (price < lowestPrice) {
						lowestPrice = price;
						endpoint = supermarketsProductList.get(productsHash);
					}
				}
				addProduct(listId, endpoint, product);
				listPrice += lowestPrice;
			}

			return new LowestPrice(listId, listPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void addProduct(String listId, String endpoint, String product) {
		if (customerProductLists.get(listId).get(endpoint) == null) {
			customerProductLists.get(listId).put(endpoint,
					new HashSet<String>());
		}
		customerProductLists.get(listId).get(endpoint).add(product);
	}

	private synchronized long getListId() {
		return currentList++;
	}

	@WebMethod
	public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo) {
		RegistryWS registryWS;
		try {
			registryWS = WsdlInfo.getPort(getRegistryWsdl(), RegistryWS.class);
			ShipperWS shipperWS = WsdlInfo.getPort(registryWS.getFirst("Shipper"), ShipperWS.class);

			DeliveryInfo response = shipperWS.getDeliveryStatus(purchaseInfo);
			return response;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@WebMethod
	public PurchaseInfo[] makePurchase(String listId, CustomerInfo customerInfo) {
		HashMap<String, Set<String>> purchaseLists = customerProductLists.get(listId);
		List<PurchaseInfo> result = new ArrayList<PurchaseInfo>();

		//for each supermarket
		for(String endpoint: purchaseLists.keySet()) {
			String[] products = (String[]) purchaseLists.get(endpoint).toArray();
			SupermarketWS supermarketWS = WsdlInfo.getPort(endpoint, SupermarketWS.class);
			PurchaseInfo purchaseInfo = supermarketWS.purchase(products, customerInfo);
			result.add(purchaseInfo);
		}

		return result.toArray(new PurchaseInfo[1]);
	}

}
