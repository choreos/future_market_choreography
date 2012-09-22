package br.usp.ime.futuremarket.tests.functional.choreography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.ProductList;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.Portal;
import br.usp.ime.futuremarket.orchestration.PortalImpl;

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
    private ShopList cheapList;
    private ProductList list;

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
        list = new ProductList(prodName);

        // Searching for lowest price
        cheapList = portal.getLowestPrice(list);
        cheapItem = cheapList.getShopListItems().iterator().next();
        cheapProd = cheapItem.getProduct();

        assertEquals(prodName, cheapProd.getName());
        assertEquals(1.0, cheapProd.getPrice(), 0.01);
        assertEquals(market.getBaseAddress(cheapestSm), cheapItem.getSeller());
    }
    
    @Test
    public void testPortalLowestPrice() throws Exception {
    	Portal myPortal = new PortalImpl();
    	list = getProductList();
    	
    	cheapList = myPortal.getLowestPrice(list);
    	
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
    
    protected ProductList getProductList() {
		final ProductList list = new ProductList();
		for (int i = 1; i < 6; i++) {
			list.put(getProductName(i), 2);
		}
		return list;
	}
    
    protected String getProductName(final int number) {
    	return "product" + number;
    }
    
    protected String getCheapestSm(final Product product) {
        final String prodName = product.getName();
        return prodName.replaceFirst("product", "supermarket");
    }
}