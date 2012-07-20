package br.usp.ime.futuremarket.choreography;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.DeliveryInfo;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.ProductPrice;
import br.usp.ime.futuremarket.choreography.ProductQuantity;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;
import br.usp.ime.futuremarket.choreography.Shipper;
import br.usp.ime.futuremarket.choreography.Supermarket;
import br.usp.ime.futuremarket.choreography.models.LowestPrice;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
endpointInterface = "br.usp.ime.futuremarket.Customer")
public class BrokerImpl implements Broker {

	private static final String REL_PATH = "customer/customer";

	private FutureMarket futureMarket;
	private List<Supermarket> supermarkets;
	// <listID, <supermarket,<productquantity>>>
	private Map<String, Map<Supermarket, Set<ProductQuantity>>> customerProductLists;
	private long currentList;

	public BrokerImpl() {
		customerProductLists = new HashMap<String, Map<Supermarket, Set<ProductQuantity>>>();
		currentList = 0L;
		futureMarket = new FutureMarket();
		futureMarket.register(FutureMarket.CUSTOMER_ROLE, "Customer", REL_PATH);
	}

	private List<Supermarket> getSupermarkets() {
		return futureMarket.getClients(FutureMarket.SUPERMARKET_ROLE,
				FutureMarket.SUPERMARKET_SERVICE, Supermarket.class);
	}

	@WebMethod
	public LowestPrice getLowestPriceForList(Set<ProductQuantity> products) {
		HashMap<HashMap<String, Double>, Supermarket> supermarketsProductList = new HashMap<HashMap<String, Double>, Supermarket>();
		// gets prices from supermarkets
		supermarkets = getSupermarkets();

		for (Supermarket supermarket : supermarkets) {
			ProductPrice[] productPrices = supermarket.getPrices(products);
			HashMap<String, Double> productsMap = new HashMap<String, Double>();
			for (ProductPrice productPrice : productPrices) {
				productsMap.put(productPrice.getProduct(), productPrice.getPrice());
			}
			supermarketsProductList.put(productsMap, supermarket);
		}

		final String listId = Long.toString(getListId());
		initializeMap(listId);
		Double listPrice = 0d;
		// finds lowest prices
		for (ProductQuantity product : products) {
			Supermarket supermarket = null;
			Double lowestPrice = Double.MAX_VALUE;
			for (HashMap<String, Double> productsHash : supermarketsProductList.keySet()) {
				Double price = productsHash.get(product.getProduct());
				if (price < lowestPrice) {
					lowestPrice = price;
					supermarket = supermarketsProductList.get(productsHash);
				}
			}
			addProduct(listId, supermarket, product);
			listPrice += (lowestPrice  * product.getQuantity());
		}

		return new LowestPrice(listId, listPrice);
	}

	private void initializeMap(String listId) {
		synchronized (this) {
			customerProductLists.put(listId, new HashMap<Supermarket, Set<ProductQuantity>>());
		}
	}

	private void addProduct(String listId, Supermarket supermarket, ProductQuantity product) {
		synchronized (this) {
			if (customerProductLists.get(listId).get(supermarket) == null) {
				customerProductLists.get(listId).put(supermarket, new HashSet<ProductQuantity>());
			}
			customerProductLists.get(listId).get(supermarket).add(product);
		}
	}

	private synchronized long getListId() {
		return currentList++;
	}

	@WebMethod
	public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo) {
		Shipper shipper  = futureMarket.getClientByName(purchaseInfo.getShipperName(),
				FutureMarket.SHIPPER_SERVICE, Shipper.class);
		return shipper.getDeliveryStatus(purchaseInfo);
	}

	@WebMethod
	public PurchaseInfo[] makePurchase(final String listId, final CustomerInfo customerInfo) {
		List<PurchaseInfo> result = new ArrayList<PurchaseInfo>();
		Map<Supermarket, Set<ProductQuantity>> purchaseLists = null;

		synchronized (this) {
			purchaseLists = customerProductLists.remove(listId);
		}

		if (purchaseLists != null) {
			buy(customerInfo, result, purchaseLists);
			purchaseLists.clear();
		}

		return result.toArray(new PurchaseInfo[0]);
	}

	private void buy(final CustomerInfo customerInfo, final List<PurchaseInfo> result,
			final Map<Supermarket, Set<ProductQuantity>> purchaseLists) {
		PurchaseInfo purchaseInfo;

		for (Supermarket supermarket : purchaseLists.keySet()) {
			/* HashMap<String, Integer> productQuantities = new HashMap<String, Integer>();            
            System.out.println("customer ======================================");
            for (String p: purchaseLists.get(supermarket)) {
            	System.out.println("customer adding " + p);
            	productQuantities.put(p, 1);
            }
            System.out.println("productQuantities: " + productQuantities);*/
			purchaseInfo = supermarket.purchase(purchaseLists.get(supermarket), customerInfo);
			result.add(purchaseInfo);
		}
	}
}