package br.usp.ime.futuremarket.choreography.tests.functional;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.choreography.Broker;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.Role;
import br.usp.ime.futuremarket.choreography.ServiceName;

/**
 * Use case 3
 * 
 * @author cadu
 * 
 */
public class BrokerTest {
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
        final FutureMarket market = new FutureMarket();
        final Broker broker = market.getClientByRole(Role.BROKER, ServiceName.BROKER, Broker.class);
        final ShopList cheapList = broker.getLowestPrice(list);
        final ShopListItem cheapItem = cheapList.getShopListItems().iterator().next();
        final Product cheapProd = cheapItem.getProduct();

        assertEquals(productName, cheapProd.getName());
        assertEquals(1.0, cheapProd.getPrice(), 0.01);
        assertEquals(market.getBaseAddress(cheapestSm), cheapItem.getSeller());
    }
}