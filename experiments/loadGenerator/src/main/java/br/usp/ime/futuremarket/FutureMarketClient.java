package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import br.usp.ime.futuremarket.choreography.Portal;

@SuppressWarnings("PMD.MoreThanOneLogger")
public class FutureMarketClient implements Runnable {

    private static final ShopList SHOPLIST = getShopList();

    private static FrequencyHelper freqHelper;
    private static List<Portal> portals;

    private static final Logger GRAPH = Logger.getLogger("graphsLogger");
    private static final Logger CONSOLE = Logger.getLogger(FutureMarketClient.class);

    private final Portal portal;
    private final int threadNumber;
    private final CustomerInfo myInfo;

    public static void setPortals(final List<Portal> portals) throws IOException {
        FutureMarketClient.portals = portals;
    }

    public static void setFrequencyHelper(final FrequencyHelper freqHelper) {
        FutureMarketClient.freqHelper = freqHelper;
    }

    public FutureMarketClient(final int threadNumber) {
        CONSOLE.debug("Thread " + threadNumber + " has started.");
        this.threadNumber = threadNumber;
        portal = portals.get(threadNumber % portals.size());
        myInfo = getCustomerInfo();
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
            GRAPH.error("Purchase test! Length is not 5: " + purchases.size());
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
        if (delivery == null || !delivery.getStatus().equals("delivered")) {
            GRAPH.error("Shipment test failed!");
        }
    }

    private ShopList getLowestPriceList() throws IOException {
        final ShopList list = portal.getLowestPrice(SHOPLIST);
        verifyLowestPriceList(list);
        return list;
    }

    private void verifyLowestPriceList(final ShopList list) {
        if (Math.abs(list.getPrice() - 1215) > 0.01) {
            GRAPH.error("Price list test failed. Price is not 1215: " + list.getPrice());
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < freqHelper.getTotalRequests(threadNumber); i++) {
            try {
                final long sleepTime = freqHelper.getSleepTime(threadNumber, i);
                if (sleepTime < 0) {
                    GRAPH.info("-1");
                    continue;
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                GRAPH.error("Sleeping", e);
            }
            try {
                simulate();
            } catch (IOException e) {
                GRAPH.error("simulate()", e);
            }
        }
    }

    private void simulate() throws IOException {
        final long start = Calendar.getInstance().getTimeInMillis();

        final ShopList list = getLowestPriceList();
        final Set<Purchase> purchases = purchase(list);
        requestDeliveries(purchases);

        final long end = Calendar.getInstance().getTimeInMillis();
        GRAPH.info(end - start);
    }
}