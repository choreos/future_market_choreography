package br.usp.ime.futuremarket.choreography.tests.acceptance;

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
 * Acceptance test for use case 3
 * 
 * @author cadu
 * 
 */
public class AcceptanceTest {
    @Test
    public void testLowestPriceList() throws IOException {
        final FutureMarket market = new FutureMarket();
        final Broker broker = market.getClientByRole(Role.BROKER, ServiceName.BROKER, Broker.class);
        final ShopList list = getShopList();
        final ShopList cheapList = broker.getLowestPrice(list);

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

    // TODO Test other two operations

    private String getCheapestSm(final Product product) {
        final String prodName = product.getName();
        return prodName.replaceFirst("product", "supermarket");
    }

    private ShopList getShopList() {
        final ShopList list = new ShopList();
        Product product;
        ShopListItem item;

        for (int i = 1; i < 6; i++) {
            product = getProduct(i);
            item = getItem(product);
            list.put(item);
        }

        return list;
    }

    private ShopListItem getItem(final Product product) {
        final ShopListItem item = new ShopListItem(product);
        item.setQuantity(2);

        return item;
    }

    private Product getProduct(final int number) {
        return new Product("product" + number);
    }
}