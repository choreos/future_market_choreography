package br.usp.ime.futuremarket.tests.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
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
        String cheapestSm;
        for (ShopListItem item : cheapList.getShopListItems()) {	
            cheapestSm = getCheapestSm(item.getProduct());
            
            assertEquals(cheapestSm, item.getSeller());
        }
    }

    @Test
    public void testPurchase() throws IOException {
        final CustomerInfo customer = getCustomerInfo();
        final ShopList list = getShopList(true);
        final Set<String> supermarkets = new HashSet<String>();

        final Set<Purchase> purchases = portal.purchase(list, customer);
        assertEquals(5, purchases.size());

        // Each purchase should have a different seller by getShopList
        int numberOfProducts;
        for (Purchase purchase : purchases) {
            // Seller uniqueness
            assertFalse(supermarkets.contains(purchase.getSeller()));
            supermarkets.add(purchase.getSeller());

            numberOfProducts = purchase.getShopList().getProducts().size();
            assertEquals(1, numberOfProducts);

            assertTrue(purchase.isPaid());
            assertEquals(getShipper(purchase.getSeller()), purchase.getShipper());
        }
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

    protected String getShipper(final String seller) throws IOException {
        final String sellerNumberStr = seller.substring(seller.length() - 1);
        final int sellerNumber = Integer.parseInt(sellerNumberStr);

        final String shipperNumber = (sellerNumber % 2 == 0) ? "2" : "1";
        final String shipperName = "shipper" + shipperNumber;

        return shipperName;
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

    protected String getCheapestSm(final Product product) {
        final String prodName = product.getName();
        return prodName.replaceFirst("product", "supermarket");
    }

    protected ShopList getShopList(final boolean setLowestPriceSm) throws IOException {
        final ShopList list = new ShopList();
        Product product;
        ShopListItem item;

        for (int i = 1; i < 6; i++) {
            product = getProduct(i);
            item = getItem(product);
            if (setLowestPriceSm) {
                item.setSeller(getCheapestSm(product));
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