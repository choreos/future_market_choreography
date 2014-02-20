package br.usp.ime.futuremarket.tests.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.futuremarket.AbstractFutureMarket;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.choreography.Portal;

public abstract class AbstractAcceptanceTest {

    private static final String ADDRESS = "42 St";
    private static Portal portal;
    protected static AbstractFutureMarket market;
    protected final static String ROLE = "portal";
    private static final String SM_SHIPPER = "shipper1";
    private static final String SM_NAME = "supermarket1";

    @Before
    public void setUp() throws IOException {
        if (market == null) {
            market = getFutureMarket();
            portal = getPortal();
        }
    }

    @AfterClass
    // static vars are shared between children
    public static void reset() {
        market = null;
        portal = null;
    }

    abstract protected AbstractFutureMarket getFutureMarket() throws IOException;

    // There is no polymorphism in WS because of different package names
    abstract protected Portal getPortal() throws IOException;

    @Test
    public void testLowestPriceList() throws IOException {
        final ShopList list = getShopList(false);
        final ShopList cheapList = portal.getLowestPrice(list);

        assertEquals(5, cheapList.getShopListItems().size());
        assertEquals(10.0, cheapList.getPrice(), 0.01);

        // Checking the seller. productX is supermarketX by definition of
        // properties files
        for (ShopListItem item : cheapList.getShopListItems()) {
            assertEquals(SM_NAME, item.getSeller());
        }
    }

    @Test
    public void testPurchase() throws IOException {
        final CustomerInfo customer = getCustomerInfo();
        final ShopList list = getShopList(true);

        final Set<Purchase> purchases = portal.purchase(list, customer);
        assertEquals(1, purchases.size());
        final Purchase purchase = purchases.iterator().next();

        final int numberOfProducts = purchase.getShopList().getProducts().size();
        assertEquals(5, numberOfProducts);

        assertTrue(purchase.isPaid());
        assertEquals(SM_SHIPPER, purchase.getShipper());
    }

    @Test
    public void testShipment() throws IOException {
        final ShopList list = portal.getLowestPrice(getShopList(false));
        final CustomerInfo customer = getCustomerInfo();
        final Set<Purchase> purchases = portal.purchase(list, customer);

        Delivery delivery;
        for (Purchase purchase : purchases) {
            delivery = portal.getDelivery(purchase);
            assertNotNull(delivery.getDate());
            assertEquals("delivered", delivery.getStatus());
            checkPurchase(purchase, delivery.getPurchase());
        }
    }

    protected void checkPurchase(final Purchase expected, final Purchase actual) {
        assertEquals(ADDRESS, actual.getCustomerInfo().getAddress());
        assertTrue(actual.isPaid());
        assertEquals(expected.getUniqueId(), actual.getUniqueId());
        assertEquals(expected.getPrice(), actual.getPrice(), 0.01);
        assertEquals(expected.getShipper(), actual.getShipper());
        assertEquals(expected.getShopList().getSeller(), actual.getShopList().getSeller());
    }

    protected CustomerInfo getCustomerInfo() {
        final CustomerInfo customer = new CustomerInfo();
        customer.setName("name");
        customer.setAddress(ADDRESS);
        customer.setCreditCard("1111111111111111");
        return customer;
    }

    protected ShopList getShopList(final boolean setLowestPriceSm) throws IOException {
        final ShopList list = new ShopList();
        Product product;
        ShopListItem item;

        for (int i = 1; i < 6; i++) {
            product = getProduct(i);
            item = getItem(product);
            if (setLowestPriceSm) {
                item.setSeller(SM_NAME);
            }
            list.put(item);
        }

        return list;
    }

    protected ShopListItem getItem(final Product product) {
        final ShopListItem item = new ShopListItem(product);
        item.setQuantity(2);

        return item;
    }

    protected Product getProduct(final int number) {
        return new Product("product" + number);
    }
}