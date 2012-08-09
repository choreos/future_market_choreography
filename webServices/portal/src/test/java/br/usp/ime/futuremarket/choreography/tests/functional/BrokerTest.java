package br.usp.ime.futuremarket.choreography.tests.functional;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.usp.ime.futuremarket.choreography.Broker;
import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.ProductQuantity;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;
import br.usp.ime.futuremarket.choreography.models.LowestPrice;

public class BrokerTest {

    @Test
    public void shouldBeAbleToMakePurchase() {
        final FutureMarket futureMarket = new FutureMarket();
        final Broker broker = futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Broker.class);

        final Set<ProductQuantity> products = new HashSet<ProductQuantity>();
        products.add(new ProductQuantity("product1", 1));
        
        final LowestPrice list = broker.getLowestPriceForList(products);

        final CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");

        final PurchaseInfo[] purchases = broker.makePurchase(list.getId(), customerInfo);

        assertTrue(purchases.length > 0);
    }

    @Test
    public void shouldRemoveSupermarketListAfterPurchase() {
        final FutureMarket futureMarket = new FutureMarket();
        final Broker broker = futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Broker.class);

        final Set<ProductQuantity> products = new HashSet<ProductQuantity>();
        products.add(new ProductQuantity("product1", 1));
        final LowestPrice list = broker.getLowestPriceForList(products);

        final CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");

        PurchaseInfo[] purchases = broker.makePurchase(list.getId(), customerInfo);
        assertTrue(purchases.length > 0);

        purchases = broker.makePurchase(list.getId(), customerInfo);
        assertTrue(purchases.length == 0);
    }
}
