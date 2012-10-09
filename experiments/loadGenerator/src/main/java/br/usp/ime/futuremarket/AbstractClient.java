package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import br.usp.ime.futuremarket.choreography.Portal;

@SuppressWarnings("PMD.MoreThanOneLogger")
public abstract class AbstractClient {

    protected static final Logger GRAPH = Logger.getLogger("graphsLogger");
    protected static final Logger CONSOLE = Logger.getLogger(AbstractClient.class);
    private static final ShopList SHOPLIST = getShopList();

    private static List<Portal> portals;

    protected static CountDownLatch latch;
    protected final int threadNumber;
    protected final Portal portal;
    protected final CustomerInfo myInfo;

    protected abstract void simulate() throws IOException;

    public AbstractClient(final int threadNumber) {
        CONSOLE.debug("Thread " + threadNumber + " has started.");
        this.threadNumber = threadNumber;
        portal = portals.get(threadNumber % portals.size());
        myInfo = getCustomerInfo();
    }

    public static void setPortals(final AbstractPortalProxy portals) throws MalformedURLException {
        AbstractClient.portals = getPortalProxies(portals);
    }

    public static void setCountDownLatch(final CountDownLatch latch) {
        AbstractClient.latch = latch;
    }
    
    private CustomerInfo getCustomerInfo() {
        final CustomerInfo info = new CustomerInfo();
        info.setAddress("Rua do Matao");
        info.setCreditCard("123 456 789");
        info.setName("Thread" + threadNumber);

        return info;
    }

    private static List<Portal> getPortalProxies(final AbstractPortalProxy portals)
            throws MalformedURLException {
        final List<Portal> proxies = new ArrayList<Portal>();

        for (int i = 0; i < portals.size(); i++) {
            proxies.add(portals.getPortal(i));
        }

        return proxies;
    }

    private static ShopList getShopList() {
        final ShopList list = new ShopList();
        String product;
        int quantity;
        ShopListItem item;

        for (int i = 1; i < 11; i++) {
            product = "product" + i;
            quantity = i;
            item = getShopListItem(product, quantity);
            list.put(item);
        }

        return list;
    }

    private static ShopListItem getShopListItem(final String productName, final int quantity) {
        final Product product = new Product(productName);
        final ShopListItem item = new ShopListItem(product);
        item.setQuantity(quantity);

        return item;
    }

    protected Set<Purchase> purchase(final ShopList list) throws IOException {
        final Set<Purchase> purchases = portal.purchase(list, myInfo);
        verifyPurchase(purchases);
        return purchases;
    }

    private void verifyPurchase(final Set<Purchase> purchases) {
        if (purchases.size() != 5) {
            logError("Purchase test! Length is not 5: " + purchases.size());
        }
    }

    protected void requestDeliveries(final Set<Purchase> purchases) throws MalformedURLException {
        Delivery delivery;

        for (Purchase purchase : purchases) {
            delivery = portal.getDelivery(purchase);
            verifyDelivery(delivery);
        }
    }

    private void verifyDelivery(final Delivery delivery) {
        if (delivery == null) {
            logError("Shipment test failed! Delivery is null.");
        } else if (!delivery.getStatus().equals("delivered")) {
            logError("Shipment test failed! Status is not 'delivered'.");
        }
    }

    protected ShopList getLowestPriceList() throws IOException {
        final ShopList list = portal.getLowestPrice(SHOPLIST);
        verifyLowestPriceList(list);
        return list;
    }

    private void verifyLowestPriceList(final ShopList list) {
        if (Math.abs(list.getPrice() - 55) > 0.01) {
            logError("Price list test failed. Price is not 55: " + list.getPrice());
        }
    }

    protected void logError(final String message) {
        GRAPH.error("# ERROR " + message);
        CONSOLE.error(message);
    }

    protected void logError(final String message, final Exception exception) {
        GRAPH.error("# ERROR " + message);
        CONSOLE.error(message, exception);
    }
}
