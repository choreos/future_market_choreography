package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import br.usp.ime.futuremarket.choreography.Portal;

@SuppressWarnings("PMD.MoreThanOneLogger")
public class FutureMarketClient implements Runnable {

    // Milliseconds
    private static long timeout;
    private static AtomicInteger successes;
    private static AtomicInteger failures;

    private static final ShopList SHOPLIST = getShopList();

    private static CountDownLatch latch;
    private static List<Portal> portals;

    private static final Logger GRAPH = Logger.getLogger("graphsLogger");
    private static final Logger CONSOLE = Logger.getLogger(FutureMarketClient.class);

    private final Portal portal;
    private final int threadNumber;
    private final CustomerInfo myInfo;

    public static void setUp(final long milliseconds, final AbstractPortalProxy portals)
            throws MalformedURLException {
        FutureMarketClient.timeout = milliseconds;
        FutureMarketClient.portals = getPortalProxies(portals);
    }

    private static List<Portal> getPortalProxies(AbstractPortalProxy portals)
            throws MalformedURLException {
        final List<Portal> proxies = new ArrayList<Portal>();

        for (int i = 0; i < portals.size(); i++) {
            proxies.add(portals.getPortal(i));
        }

        return proxies;
    }

    public FutureMarketClient(final int threadNumber) throws MalformedURLException {
        CONSOLE.debug("Thread " + threadNumber + " has started.");
        this.threadNumber = threadNumber;
        portal = portals.get(threadNumber % portals.size());
        myInfo = getCustomerInfo();
    }

    public static void resetStatistics() {
        successes = new AtomicInteger(0);
        failures = new AtomicInteger(0);
    }

    public static void setCountDownLatch(final CountDownLatch latch) {
        FutureMarketClient.latch = latch;
    }

    public static int getFailures() {
        return failures.get();
    }

    public static int getSuccesses() {
        return successes.get();
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

    private Set<Purchase> purchase(final ShopList list) throws IOException {
        final Set<Purchase> purchases = portal.purchase(list, myInfo);
        verifyPurchase(purchases);
        return purchases;
    }

    private void verifyPurchase(final Set<Purchase> purchases) {
        if (purchases.size() != 5) {
            logError("Purchase test! Length is not 5: " + purchases.size());
        }
    }

    private CustomerInfo getCustomerInfo() {
        final CustomerInfo info = new CustomerInfo();
        info.setAddress("Rua do Matao");
        info.setCreditCard("123 456 789");
        info.setName("Thread" + threadNumber);

        return info;
    }

    private void requestDeliveries(final Set<Purchase> purchases) throws MalformedURLException {
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

    private ShopList getLowestPriceList() throws IOException {
        final ShopList list = portal.getLowestPrice(SHOPLIST);
        verifyLowestPriceList(list);
        return list;
    }

    private void verifyLowestPriceList(final ShopList list) {
        if (Math.abs(list.getPrice() - 55) > 0.01) {
            logError("Price list test failed. Price is not 55: " + list.getPrice());
        }
    }

    @Override
    public void run() {
        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e1) {
            logError("CountDownLatch await error", e1);
        }

        try {
            simulate();
        } catch (IOException e) {
            logError("simulate()", e);
            failures.addAndGet(1);
        }
    }

    private void simulate() throws IOException {
        final long start = Calendar.getInstance().getTimeInMillis();

        final ShopList list = getLowestPriceList();
        final Set<Purchase> purchases = purchase(list);
        requestDeliveries(purchases);

        final long end = Calendar.getInstance().getTimeInMillis();
        checkResponseTime(end - start);
    }

    private void checkResponseTime(final long time) {
        if (time < timeout) {
            successes.addAndGet(1);
        } else {
            failures.addAndGet(1);
        }
    }

    private void logError(final String message) {
        GRAPH.error("ERROR " + message);
        CONSOLE.error(message);
    }

    private void logError(final String message, final Exception exception) {
        GRAPH.error("ERROR " + message, exception);
        CONSOLE.error(message, exception);
    }
}