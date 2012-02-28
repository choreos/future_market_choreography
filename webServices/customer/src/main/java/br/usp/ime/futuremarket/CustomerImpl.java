package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.futuremarket.models.LowestPrice;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Customer")
public class CustomerImpl implements Customer {

    private static final String REL_PATH = "customer/customer";

    private FutureMarket futureMarket;
    private Shipper shipper;
    private List<Supermarket> supermarkets;
    // <listID, <supermarket,<product>>>
    private ConcurrentMap<String, ConcurrentMap<Supermarket, Set<String>>> customerProductLists;
    private long currentList;

    public CustomerImpl() {
        customerProductLists = new ConcurrentHashMap<String, ConcurrentMap<Supermarket, Set<String>>>();
        currentList = 0L;

        futureMarket = new FutureMarket();
        futureMarket.register(FutureMarket.CUSTOMER_ROLE, REL_PATH);
        shipper = futureMarket.getFirstClient(FutureMarket.SHIPPER_ROLE,
                FutureMarket.SHIPPER_SERVICE, Shipper.class);
    }

    private List<Supermarket> getSupermarkets() {
        return futureMarket.getClients(FutureMarket.SUPERMARKET_ROLE,
                FutureMarket.SUPERMARKET_SERVICE, Supermarket.class);
    }

    @WebMethod
    public LowestPrice getLowestPriceForList(String[] products) {
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

        String listId = "" + getListId();
        customerProductLists.put(listId, new ConcurrentHashMap<Supermarket, Set<String>>());
        Double listPrice = 0d;
        // finds lowest prices
        for (String product : products) {
            Supermarket supermarket = null;
            Double lowestPrice = Double.MAX_VALUE;
            for (HashMap<String, Double> productsHash : supermarketsProductList.keySet()) {
                Double price = productsHash.get(product);
                if (price < lowestPrice) {
                    lowestPrice = price;
                    supermarket = supermarketsProductList.get(productsHash);
                }
            }
            addProduct(listId, supermarket, product);
            listPrice += lowestPrice;
        }

        return new LowestPrice(listId, listPrice);
    }

    private void addProduct(String listId, Supermarket supermarket, String product) {
        final ConcurrentMap<Supermarket, Set<String>> supermarketLists = customerProductLists
                .get(listId);

        if (supermarketLists.get(supermarket) == null) {
            supermarketLists.put(supermarket, new HashSet<String>());
        }

        supermarketLists.get(supermarket).add(product);
    }

    private long getListId() {
        synchronized (this) {
            currentList++;
        }

        return currentList;
    }

    @WebMethod
    public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo) {
        return shipper.getDeliveryStatus(purchaseInfo);
    }

    @WebMethod
    public PurchaseInfo[] makePurchase(final String listId, final CustomerInfo customerInfo) {
        List<PurchaseInfo> result = new ArrayList<PurchaseInfo>();
        final Map<Supermarket, Set<String>> purchaseLists = customerProductLists.remove(listId);

        if (purchaseLists != null) {
            buy(customerInfo, purchaseLists, result);
            purchaseLists.clear();
        }

        return result.toArray(new PurchaseInfo[0]);
    }

    private void buy(final CustomerInfo customerInfo,
            final Map<Supermarket, Set<String>> purchaseLists, final List<PurchaseInfo> result) {
        final Set<Supermarket> supermarkets = purchaseLists.keySet();
        String[] products;
        PurchaseInfo purchaseInfo;

        for (Supermarket supermarket : supermarkets) {
            products = purchaseLists.get(supermarket).toArray(new String[0]);
            purchaseInfo = supermarket.purchase(products, customerInfo);
            result.add(purchaseInfo);
        }
    }
}