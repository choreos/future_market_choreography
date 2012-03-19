package br.usp.ime.futuremarket;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.models.LowestPrice;

public class AcceptanceTest {
    private static Customer customer;
    private LowestPrice list;
	private static Set<ProductQuantity> products;
	private static FutureMarket futureMarket;
	private static CustomerInfo customerInfo;
	
	@BeforeClass
	public static void initProductQuantity(){
	      products = new HashSet<ProductQuantity>();
	}
	
	@BeforeClass
	public static void initCustomerInfo(){
		customerInfo = new CustomerInfo();
        customerInfo.setName("JUnit test");
        customerInfo.setZipcode("01234-567");
	}
	
    @BeforeClass
    public static void createClients() {
        futureMarket = new FutureMarket();
        customer = futureMarket.getFirstClient(FutureMarket.CUSTOMER_ROLE,

        FutureMarket.CUSTOMER_SERVICE, Customer.class);
    }

    @Test
    // SM1
    public void product1LowestPriceShouldBe1() {
    	products = new HashSet<ProductQuantity>();
        products.add(new ProductQuantity("product1",1));
        list = customer.getLowestPriceForList(products);

        assertEquals(1.0d, list.getPrice(), 0.01d);
    }

    @Test
    // SM2
    public void product2LowestPriceShouldBe10() {
    	products = new HashSet<ProductQuantity>();
        products.add(new ProductQuantity("product2",1));
        list = customer.getLowestPriceForList(products);

        assertEquals(1.0d, list.getPrice(), 0.01d);
    }

    @Test
    // SM3
    public void product3LowestPriceShouldBe1() {
    	products = new HashSet<ProductQuantity>();
        products.add(new ProductQuantity("product3",1));
        list = customer.getLowestPriceForList(products);

        assertEquals(1.0d, list.getPrice(), 0.01d);
    }

    @Test
    // All SMs
    public void shouldAddProductPrices() {
    	products = new HashSet<ProductQuantity>();
    	products.add(new ProductQuantity("product3",1));
    	products.add(new ProductQuantity("product2",1));
    	products.add(new ProductQuantity("product1",1));
        list = customer.getLowestPriceForList(products);
        
        assertEquals(1d+1d+1d, list.getPrice(),0.01d);
    }

    @Test
    public void clientShouldBeAbleToPurchase() {
    	products = new HashSet<ProductQuantity>();
    	products.add(new ProductQuantity("product3",1));
    	products.add(new ProductQuantity("product2",1));
    	products.add(new ProductQuantity("product1",1));
        list = customer.getLowestPriceForList(products);
        
        final PurchaseInfo[] purchaseInfos = customer.makePurchase(list.getId(), customerInfo);
        assertEquals(1, purchaseInfos.length);
    }

    @Test
    public void supermarketShouldBeAbleToRenewStock(){
    	products = new HashSet<ProductQuantity>();
    	products.add(new ProductQuantity("product1",200));
    	list = customer.getLowestPriceForList(products);
    	final PurchaseInfo[] purchaseInfos = customer.makePurchase(list.getId(), customerInfo);
    	assertEquals(1, purchaseInfos.length);
    }	
    
    @Test
    public void clientShouldBeAbleToGetShipmentData() {
    	products = new HashSet<ProductQuantity>();
    	products.add(new ProductQuantity("product3",1));
    	products.add(new ProductQuantity("product2",1));
    	products.add(new ProductQuantity("product1",1));
        list = customer.getLowestPriceForList(products);
        
        final PurchaseInfo[] purchaseInfos = customer.makePurchase(list.getId(), customerInfo);
        final DeliveryInfo deliveryInfo = customer.getShipmentData(purchaseInfos[0]);        
        assertEquals("done", deliveryInfo.getStatus());
    }
    
    @Test
    public void supermarket1ShouldBeAbleToPurchasefromSupplier1(){
    	products = new HashSet<ProductQuantity>();
    	products.add(new ProductQuantity("product1",1));
    	final Supermarket supermarket1 = futureMarket.getClientByName("Supermarket1", FutureMarket.SUPERMARKET_SERVICE, Supermarket.class);
    	
    	PurchaseInfo purchase = supermarket1.purchase(products,customerInfo);
		assertEquals(1,purchase.getProducts().size());
    }
    @Test
    public void supplier1ShouldBeAbleToPurchasefromManufacture1(){
    	products = new HashSet<ProductQuantity>();
    	products.add(new ProductQuantity("product1",1));
    	final Supermarket supplier1 = futureMarket.getClientByName("Supplier1", FutureMarket.SUPERMARKET_SERVICE, Supermarket.class);
    	
    	PurchaseInfo purchase = supplier1.purchase(products,customerInfo);
    	assertEquals(1,purchase.getProducts().size());
    }
    
}