package br.usp.ime.futuremarket.tests.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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
    private Portal portal;
    protected AbstractFutureMarket market;
    protected final static String ROLE = "portal";

    @Before
    public void setPortal() throws IOException {
        market = getFutureMarket();
        portal = getPortal();
    }

    abstract protected AbstractFutureMarket getFutureMarket() throws IOException;

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
            assertEquals(market.getBaseAddress(cheapestSm), item.getSeller());
        }
    }

    @Test
    public void testPurchase() throws IOException {
        final CustomerInfo customer = getCustomerInfo();
        final ShopList list = getShopList(true);
        final Set<String> supermarkets = new HashSet<String>();

        final Set<Purchase> purchases = portal.purchase(list, customer);
        assertEquals(5, purchases.size());

        // Each purchase should have a different seller by definition
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
            assertEquals(0, getToday().compareTo(delivery.getDate()));
            assertEquals("delivered", delivery.getStatus());
            checkPurchase(purchase, delivery.getPurchase());
        }
    }

    protected String getShipper(final String seller) throws IOException {
        final String sellerName = seller.substring(seller.lastIndexOf('/') + 1);
        final String sellerNumberStr = sellerName.substring(sellerName.length() - 1);
        final int sellerNumberInt = Integer.parseInt(sellerNumberStr);

        final String shipperNumber = (sellerNumberInt % 2 == 0) ? "2" : "";
        final String shipperName = "shipper" + shipperNumber;

        return market.getBaseAddress(shipperName);
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
                item.setSeller(market.getBaseAddress(getCheapestSm(product)));
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

    protected Calendar getToday() {
        final Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }
}