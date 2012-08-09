package br.usp.ime.futuremarket.orchestration.tests.functional;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.usp.ime.futuremarket.models.LowestPrice;
import br.usp.ime.futuremarket.orchestration.CustomerInfo;
import br.usp.ime.futuremarket.orchestration.FutureMarket;
import br.usp.ime.futuremarket.orchestration.Orchestrator;
import br.usp.ime.futuremarket.orchestration.ProductQuantity;
import br.usp.ime.futuremarket.orchestration.PurchaseInfo;

public class CustomerTest {

    @Test
    public void shouldBeAbleToMakePurchase() {
        final FutureMarket futureMarket = new FutureMarket();
        final Orchestrator customer = futureMarket.getFirstClient(FutureMarket.ORCHESTRATOR_ROLE,
                FutureMarket.ORCHESTRATOR_SERVICE, Orchestrator.class);

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
        final Orchestrator customer = futureMarket.getFirstClient(FutureMarket.ORCHESTRATOR_ROLE,
                FutureMarket.ORCHESTRATOR_SERVICE, Orchestrator.class);

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
