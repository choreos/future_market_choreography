package br.usp.ime.futuremarket.tests.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.AbstractFutureMarket;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Registry;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.Supermarket;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.exceptions.MockDeploymentException;
import eu.choreos.vv.exceptions.WSDLException;
import eu.choreos.vv.interceptor.MessageInterceptor;

public abstract class AbstractSupermarketTest {
    protected static AbstractFutureMarket market;
    private static String sellerBackup;
    private static final String NAME = "supermarket1";
    private static final String SELLER = "supplier1";
    private static final String INTERCEPTOR = "http://127.0.0.1:8081/supplier1";
    private static Supermarket supermarket;
    private MessageInterceptor interceptor;
    
    private static final int QT_INITIAL = 10;
    private static final int QT_TRIGGER = 3;
    private static final int QT_PURCHASE = 10;
    private String product;

    @Before
    public void setUp() throws IOException {
        if (supermarket == null) {
            onlyOnce();
        }
        supermarket.reset();
    }
    
    private void onlyOnce() throws IOException {
        supermarket = market.getClientByName(NAME, Supermarket.class);
        sellerBackup = market.getBaseAddress(SELLER);
        registerInterceptor();
    }
    
    private void registerInterceptor() throws IOException {
        final Registry registry = market.getRegistry();
        registry.addService(Role.SUPPLIER.toString(), SELLER, INTERCEPTOR);
    }

    @Before
    public void loadInterceptor() throws WSDLException, MockDeploymentException, XmlException,
            IOException {
        interceptor = new MessageInterceptor("8081");
        interceptor.interceptTo("http://127.0.0.1:8080/supplier1/choreography?wsdl");
    }

    @After
    public void stopInterceptor() {
        interceptor.stop();
    }

    @AfterClass
    public static void restoreSeller() throws IOException {
        final Registry registry = market.getRegistry();
        registry.addService(Role.SUPPLIER.toString(), SELLER, sellerBackup);
    }

    @Test
    public void shouldNotBuyWhenQuantityIsHigherThanTrigger() throws IOException {
        buy("product1", QT_INITIAL - QT_TRIGGER - 1);
        final List<Item> messages = interceptor.getMessages();
        assertEquals(0, messages.size());
    }

    @Test
    public void shouldBuyWhenQuantityEqualsTrigger() throws IOException, NoSuchFieldException {
        product = "product1";
        buy(product, QT_INITIAL - QT_TRIGGER);
        final List<Item> messages = interceptor.getMessages();
        
        assertEquals(1, messages.size());
        assertEquals(QT_PURCHASE, getPurchasedQuantity(messages.get(0)));
        assertEquals(product, getProductName(messages.get(0)));
    }

    @Test
    public void shouldBuyWhenQuantityIsLowerThanTrigger() throws IOException, NoSuchFieldException {
        product = "product2";
        buy(product, QT_INITIAL - QT_TRIGGER + 1);
        final List<Item> messages = interceptor.getMessages();
        
        assertEquals(1, messages.size());
        assertEquals(QT_PURCHASE, getPurchasedQuantity(messages.get(0)));
        assertEquals(product, getProductName(messages.get(0)));
    }
    
    @Test
    public void testSupplyQuantity() throws IOException {
        product = "product3";
        
        // Before: 10 items = initial quantity
        buy(product, QT_INITIAL - QT_TRIGGER);
        List<Item> messages = interceptor.getMessages();
        assertEquals(1, messages.size());
        // After: 3 items = trigger quantity
        
        // Before: 13 items = 3 + purchase quantity
        buy(product, QT_TRIGGER + QT_PURCHASE - QT_TRIGGER - 1);
        messages = interceptor.getMessages();
        assertEquals(1, messages.size());
        // After: 4 items = trigger quantity + 1

        // One more purchase and trigger should be reached
        buy(product, 1);
        messages = interceptor.getMessages();
        assertEquals(2, messages.size());
    }
    
    @Test
    public void testSupplyQuantityAfterHugePurchase() throws IOException {
        product = "product4";
        
        // Before: 10 items = initial quantity
        buy(product, QT_INITIAL * 2);
        List<Item> messages = interceptor.getMessages();
        assertEquals(1, messages.size());
        // After: 0 items
        
        // Before: 10 items = 0 + purchase quantity
        buy(product, QT_PURCHASE - QT_TRIGGER - 1);
        messages = interceptor.getMessages();
        assertEquals(1, messages.size());
        // After: 4 items = trigger quantity + 1

        // One more purchase and trigger should be reached
        buy(product, 1);
        messages = interceptor.getMessages();
        assertEquals(2, messages.size());
    }
    
    private int getPurchasedQuantity(final Item purchaseRequest) throws NoSuchFieldException {
        final Item shopList = purchaseRequest.getChild("arg0");
        final Item items = shopList.getChild("items");
        final Item entry = items.getChild("entry");
        final Item item = entry.getChild("value");
        final String quantity = item.getContent("quantity");

        return Integer.parseInt(quantity);
    }

    private String getProductName(final Item purchaseRequest) throws NoSuchFieldException {
        final Item shopList = purchaseRequest.getChild("arg0");
        final Item items = shopList.getChild("items");
        final Item entry = items.getChild("entry");
        final Item item = entry.getChild("value");
        final Item product = item.getChild("product");
        
        return product.getContent("name");
    }

    private void buy(final String product, final int quantity) throws IOException {
        final ShopList list = getShopList(product, quantity);
        final CustomerInfo info = new CustomerInfo();
        supermarket.purchase(list, info);
    }

    private ShopList getShopList(final String productName, final int quantity) {
        final Product product = new Product(productName);
        final ShopListItem item = new ShopListItem(product);
        item.setQuantity(quantity);
        return new ShopList(item);
    }
}