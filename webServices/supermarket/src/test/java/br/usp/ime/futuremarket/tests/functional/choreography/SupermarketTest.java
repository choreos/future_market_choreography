package br.usp.ime.futuremarket.tests.functional.choreography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Configuration;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.Supermarket;
import br.usp.ime.futuremarket.choreography.FutureMarket;

public class SupermarketTest {
    private static Supermarket supermarket;

    @BeforeClass
    public static void setSupermarket() throws IOException {
        final FutureMarket market = new FutureMarket();
        final String registry = Configuration.getInstance().getRegistryWsdl();
        market.setRegistryWsdl(registry);
        supermarket = market.getClientByRole(Role.SUPERMARKET, Supermarket.class);
    }

    @Test
    public void testProxyCreation() {
        assertNotNull(supermarket);
    }

    @Test
    public void testPurchase() throws IOException {
        final CustomerInfo info = new CustomerInfo();
        final Product product = new Product("product1");
        final ShopListItem item = new ShopListItem(product);
        final ShopList list = new ShopList(item);

        final Purchase purchase = supermarket.purchase(list, info);
        assertNotNull(purchase);
        assertEquals(true, purchase.isPaid());
    }
}