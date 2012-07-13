package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.usp.ime.futuremarket.choreography.Customer;
import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.ProductQuantity;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;
import br.usp.ime.futuremarket.choreography.models.LowestPrice;

public class CustomerTest {

    @Test
    public void shouldBeAbleToMakePurchase() {
        final FutureMarket futureMarket = new FutureMarket();
        final Customer customer = futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Customer.class);

        final Set<ProductQuantity> products = new HashSet<ProductQuantity>();
        products.add(new ProductQuantity("product1", 1));
        
        final LowestPrice list = customer.getLowestPriceForList(products);

        final CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");

        final PurchaseInfo[] purchases = customer.makePurchase(list.getId(), customerInfo);

        assertTrue(purchases.length > 0);
    }

    @Test
    public void shouldRemoveSupermarketListAfterPurchase() {
        final FutureMarket futureMarket = new FutureMarket();
        final Customer customer = futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Customer.class);

        final Set<ProductQuantity> products = new HashSet<ProductQuantity>();
        products.add(new ProductQuantity("product1", 1));
        final LowestPrice list = customer.getLowestPriceForList(products);

        final CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");

        PurchaseInfo[] purchases = customer.makePurchase(list.getId(), customerInfo);
        assertTrue(purchases.length > 0);

        purchases = customer.makePurchase(list.getId(), customerInfo);
        assertTrue(purchases.length == 0);
    }
}