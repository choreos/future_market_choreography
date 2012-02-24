package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.usp.ime.futuremarket.Customer;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.FutureMarket;
import br.usp.ime.futuremarket.PurchaseInfo;
import br.usp.ime.futuremarket.models.LowestPrice;

public class CustomerTest {

    @Test
    public void shouldBeAbleToMakePurchase() {
        final FutureMarket futureMarket = new FutureMarket();
        final Customer customer = futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,
                FutureMarket.CUSTOMER_SERVICE, Customer.class);

        final String[] products = { "product1" };
        final LowestPrice list = customer.getLowestPriceForList(products);

        final CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");

        final PurchaseInfo[] purchases = customer.makePurchase(list.getId(), customerInfo);

        assertTrue(purchases.length > 0);
    }
}