package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.futuremarket.models.LowestPrice;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Customer")
public class CustomerImpl implements Customer {

    private static final String REL_PATH = "customer/customer";

    private FutureMarket futureMarket;
    private Shipper shipper;
    // <listID, <supermarket,<product>>>
    private Map<String, Map<Supermarket, Set<String>>> customerProductLists;
    private long currentList;

    public CustomerImpl() {
        customerProductLists = new HashMap<String, Map<Supermarket, Set<String>>>();
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
        Map<Supermarket, ProductPrice[]> supermarketsPrices = getSupermarketsPrices(products);
        final String listId = Long.toString(getListId());
        initializeMap(listId);

        double cheapestPrice, price, totalPrice = 0;
        Supermarket cheapestSupermarket;
        for (String product : products) {
            cheapestPrice = Double.MAX_VALUE;
            cheapestSupermarket = null;
            for (Supermarket supermarket : supermarketsPrices.keySet()) {
                price = getProductPrice(product, supermarketsPrices.get(supermarket));
                if (price < cheapestPrice) {
                    cheapestPrice = price;
                    cheapestSupermarket = supermarket;
                }
            }
            addProduct(listId, cheapestSupermarket, product);
            totalPrice += cheapestPrice;
        }

        return new LowestPrice(listId, totalPrice);
    }

    private void initializeMap(String listId) {
        synchronized (this) {
            customerProductLists.put(listId, new HashMap<Supermarket, Set<String>>());
        }
    }

    private double getProductPrice(String product, ProductPrice[] productPrices) {
        double price = -1;

        for (ProductPrice productPrice : productPrices) {
            if (product.equals(productPrice.getProduct())) {
                price = productPrice.getPrice();
                break;
            }
        }

        return price;
    }

    private Map<Supermarket, ProductPrice[]> getSupermarketsPrices(String[] products) {
        final Map<Supermarket, ProductPrice[]> supermarketsPrices = new HashMap<Supermarket, ProductPrice[]>();

        for (Supermarket supermarket : getSupermarkets()) {
            supermarketsPrices.put(supermarket, supermarket.getPrices(products));
        }

        return supermarketsPrices;
    }

    private void addProduct(String listId, Supermarket supermarket, String product) {
        synchronized (this) {
            if (customerProductLists.get(listId).get(supermarket) == null) {
                customerProductLists.get(listId).put(supermarket, new HashSet<String>());
            }
            customerProductLists.get(listId).get(supermarket).add(product);
        }
    }

    private synchronized long getListId() {
        return currentList++;
    }

    @WebMethod
    public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo) {
        return shipper.getDeliveryStatus(purchaseInfo);
    }

    @WebMethod
    public PurchaseInfo[] makePurchase(final String listId, final CustomerInfo customerInfo) {
        List<PurchaseInfo> result = new ArrayList<PurchaseInfo>();
        Map<Supermarket, Set<String>> purchaseLists = null;

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
            final Map<Supermarket, Set<String>> purchaseLists) {
        String[] products;
        PurchaseInfo purchaseInfo;

        for (Supermarket supermarket : purchaseLists.keySet()) {
            products = purchaseLists.get(supermarket).toArray(new String[1]);
            purchaseInfo = supermarket.purchase(products, customerInfo);
            shipper.setDelivery(purchaseInfo);
            result.add(purchaseInfo);
        }
    }
}