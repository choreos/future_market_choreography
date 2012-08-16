package br.usp.ime.futuremarket.choreography.tests.acceptance;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Purchase;
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
	
	private static final FutureMarket market = new FutureMarket();
	private Broker broker;
	
	@Before
	public void setup() throws Exception {
		broker = market.getClientByRole(Role.BROKER, ServiceName.BROKER, Broker.class);
	}
	
    @Test
    public void testLowestPriceList() throws IOException {
    	final ShopList list = getShopList(false);
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

    @Test
    public void testPurchase() throws Exception {
    	final CustomerInfo customer = getCustomerInfo();
    	final ShopList list = getShopList(true);
    	final Set<Purchase> purchases = broker.purchase(list, customer);
    	assertEquals(5, purchases.size());
    	
    	int numberOfProducts;
    	for(Purchase purchase : purchases) {    		
    		numberOfProducts = purchase.getShopList().getProducts().size();
    		assertEquals(1, numberOfProducts);
    	}
    }

    private CustomerInfo getCustomerInfo() {
		CustomerInfo customer = new CustomerInfo();
		customer.setName("name");
		customer.setAddress("adderss");
		customer.setCreditCard("1111111111111111");
		return customer;
	}

	// TODO Test other two operations

    private String getCheapestSm(final Product product) {
        final String prodName = product.getName();
        return prodName.replaceFirst("product", "supermarket");
    }

    private ShopList getShopList(boolean setSupermerket) throws IOException {
        final ShopList list = new ShopList();
        Product product;
        ShopListItem item;

        for (int i = 1; i < 6; i++) {
            product = getProduct(i);
            item = getItem(product);
            if (setSupermerket)
            	item.setSeller(market.getBaseAddress(getCheapestSm(product)));
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