package eu.choreos.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.xmlbeans.XmlException;

import eu.choreos.DeliveryInfo;
import eu.choreos.PurchaseInfo;
import eu.choreos.models.LowestPrice;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

@WebService
public class CustomerWS {

	private List<String> endpoints;
	static WSClient registry;
	final ClassLoader loader = CustomerWS.class.getClassLoader();
	private HashMap<String, HashMap<String, Set<String>>> customerProductLists;
	private long currentList = 1L;

	public CustomerWS() throws WSDLException, FileNotFoundException,
	XmlException, IOException, FrameworkException {
		registry = new WSClient(this.getRegistryWsdl());
		customerProductLists = new HashMap<String, HashMap<String, Set<String>>>();
	}


	private String getMyWsdl() throws MalformedURLException, UnknownHostException {
		final String hostName = getMyHostName();
		return "http://" + hostName + ":8080/smcustomer/smcustomer?wsdl";
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
			Item supermarketItem = registry.request("getList", "Supermarket");
			List<Item> supermarketList = supermarketItem.getChildAsList("return");
			HashMap<HashMap<String, Double>, String> supermarketsProductList = new HashMap<HashMap<String, Double>, String>();

			for (Item supermarketEndpoint : supermarketList) {
				String endpoint = supermarketEndpoint.getContent();
				WSClient wsSupermarket = new WSClient(endpoint);
				Item productListReturn = wsSupermarket.request("getPrices", ArrayStringtoItem(products));
				HashMap<String, Double> productsMap = new HashMap<String, Double>();
				for (Item productPrice : productListReturn
						.getChildAsList("return")) {
					productsMap.put(productPrice.getChild("product")
							.getContent(), Double.parseDouble(productPrice
									.getChild("price").getContent()));
				}
				supermarketsProductList.put(productsMap, endpoint);
			}

			String listId = "" + getListId();
			customerProductLists.put(listId, new HashMap<String, Set<String>>());
			Double listPrice = 0d;
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

	private Item ArrayStringtoItem(String[] strings) {
		Item getPrices = new ItemImpl("getPrices");
		for (String i : strings) {
			Item arg0 = new ItemImpl("arg0");
			arg0.setContent(i);
			getPrices.addChild(arg0);
		}
		return getPrices;
	}

	@WebMethod
	public PurchaseInfo makePurchase(String id, String name, String address,
			String zipcode) throws Exception {
		String listOfShipper = "";

		for (String endpoint : endpoints) {

			WSClient supermarket = new WSClient(endpoint);

			Item request = new ItemImpl("purchase");
			Item orderID = new ItemImpl("id");
			orderID.setContent(id);
			request.addChild(orderID);

			Item personalData = getPersonalData(name, address, zipcode);
			request.addChild(personalData);

			Item response = supermarket.request("purchase", request);

			listOfShipper = response.getChild("confirmation").getContent();

		}

		return null;
	}

	private Item getPersonalData(String wName, String wAddress, String wZipcode) {
		Item personalData = new ItemImpl("data");

		Item name = new ItemImpl("name");
		name.setContent(wName);
		personalData.addChild(name);

		Item address = new ItemImpl("address");
		address.setContent(wAddress);
		personalData.addChild(address);

		Item zipCode = new ItemImpl("zipcode");
		zipCode.setContent(wZipcode);
		personalData.addChild(zipCode);
		return personalData;
	}


	@WebMethod
	public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo) {
		try {
			Item shipperItem = registry.request("getFirst", "shipper");
			String endpoint = shipperItem.getChild("result").getContent();
			
			WSClient shipper = new WSClient(endpoint);
			Item deliveryItem = shipper.request("getShipmentData", purchaseInfo.getItem("arg0"));
			
			DeliveryInfo response = DeliveryInfo.fromItem(deliveryItem);
			return response;
		} catch (InvalidOperationNameException e) {
			e.printStackTrace();
		} catch (FrameworkException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
