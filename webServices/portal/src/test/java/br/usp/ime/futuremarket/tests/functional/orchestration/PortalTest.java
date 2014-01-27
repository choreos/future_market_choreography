package br.usp.ime.futuremarket.tests.functional.orchestration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Configuration;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.orchestration.FutureMarket;
import br.usp.ime.futuremarket.orchestration.Portal;

/**
 * Use case 3
 * 
 * @author cadu
 * 
 */
public class PortalTest {
    private static FutureMarket market;
    private static Portal portal;

    @BeforeClass
    public static void setPortal() throws IOException {
        market = new FutureMarket();
        market.setRegistryWsdl(Configuration.getInstance().getRegistryWsdl());
        portal = market.getDependencyByRole(Role.PORTAL, Portal.class).get(0);
    }

    @Test
    public void testProxyCreation() {
        assertNotNull(portal);
    }

    @Test
    public void testProduct1LowestPrice() throws IOException {
        final String productName = "product1";
        final String cheapestSm = "supermarket1";

        // Building ShopList
        final Product product = new Product();
        product.setName(productName);
        final ShopListItem item = new ShopListItem(product);
        final ShopList list = new ShopList();
        list.put(item);

        // Searching for lowest price
        final ShopList cheapList = portal.getLowestPrice(list);
        final ShopListItem cheapItem = cheapList.getShopListItems().iterator().next();
        final Product cheapProd = cheapItem.getProduct();

        assertEquals(productName, cheapProd.getName());
        assertEquals(1.0, cheapProd.getPrice(), 0.01);
        //assertEquals(market.getBaseAddress(cheapestSm), cheapItem.getSeller());
    }

    @Test
    public void testRequestPayment() throws IOException {
        final boolean authorized = portal.requestPayment(1.0, new CustomerInfo());
        assertTrue(authorized);
    }
}