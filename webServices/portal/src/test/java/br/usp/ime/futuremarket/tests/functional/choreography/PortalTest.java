package br.usp.ime.futuremarket.tests.functional.choreography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.Portal;

/**
 * Use case 3
 * 
 * @author cadu
 * 
 */
public class PortalTest {
    private static FutureMarket market;
    private static Portal portal;

    private Product product, cheapProd;
    private ShopListItem item, cheapItem;
    private ShopList list, cheapList;

    @BeforeClass
    public static void setPortal() throws IOException {
        market = new FutureMarket();
        portal = market.getClientByRole(Role.PORTAL, Portal.class);
    }

    @Test
    public void testProxyCreation() throws IOException {
        assertNotNull(portal);
    }

    @Test
    public void testProduct1LowestPrice() throws IOException {
        // Building ShopList
        final String prodName = "product1";
        final String cheapestSm = "supermarket1";
        product = new Product();
        product.setName(prodName);
        item = new ShopListItem(product);
        list = new ShopList();
        list.put(item);

        // Searching for lowest price
        cheapList = portal.getLowestPrice(list);
        cheapItem = cheapList.getShopListItems().iterator().next();
        cheapProd = cheapItem.getProduct();

        assertEquals(prodName, cheapProd.getName());
        assertEquals(1.0, cheapProd.getPrice(), 0.01);
        assertEquals(market.getBaseAddress(cheapestSm), cheapItem.getSeller());
    }
}