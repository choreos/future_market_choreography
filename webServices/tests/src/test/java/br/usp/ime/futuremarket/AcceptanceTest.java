package br.usp.ime.futuremarket;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.models.LowestPrice;

public class AcceptanceTest {
    private static Customer customer;

    @BeforeClass
    public static void createClients() {
        final FutureMarket futureMarket = new FutureMarket();
        customer = futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Customer.class);
    }

    @Test
    // SM1
    public void product1LowestPriceShouldBe1() {
        final String[] products = { "product1" };
        final LowestPrice price = customer.getLowestPriceForList(products);

        assertEquals(1d, price.getPrice(), 0.01d);
    }

    @Test
    // SM2
    public void product2LowestPriceShouldBe2() {
        final String[] products = { "product2" };
        final LowestPrice price = customer.getLowestPriceForList(products);

        assertEquals(2d, price.getPrice(), 0.01d);
    }

    @Test
    // SM3
    public void product3LowestPriceShouldBe3() {
        final String[] products = { "product3" };
        final LowestPrice price = customer.getLowestPriceForList(products);

        assertEquals(3d, price.getPrice(), 0.01d);
    }

    @Test
    // All SMs
    public void shouldAddProductPrices() {
        final String[] products = { "product1", "product2", "product3" };
        final LowestPrice price = customer.getLowestPriceForList(products);

        assertEquals(6d, price.getPrice(), 0.01d);
    }

    @Test
    public void clientShouldBeAbleToPurchase() {
        final String[] products = { "product1", "product2", "product3" };
        final LowestPrice list = customer.getLowestPriceForList(products);

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");
        customerInfo.setZipcode("01234-567");

        final PurchaseInfo[] purchaseInfos = customer.makePurchase(list.getId(), customerInfo);

        assertEquals(3, purchaseInfos.length);
    }

    @Test
    public void clientShouldBeAbleToGetShipmentData() {
        final String[] products = { "product1", "product2", "product3" };
        final LowestPrice list = customer.getLowestPriceForList(products);

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");
        customerInfo.setZipcode("01234-567");

        final PurchaseInfo[] purchaseInfos = customer.makePurchase(list.getId(), customerInfo);

        final DeliveryInfo deliveryInfo = customer.getShipmentData(purchaseInfos[0]);
        assertEquals("done", deliveryInfo.getStatus());
    }
}