package br.usp.ime.futuremarket;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import br.usp.ime.futuremarket.choreography.FutureMarket;

public abstract class AbstractSupermarket implements Supermarket {

    private static final int PRODUCTS = 10;

    protected final ShopList shopList = new ShopList();
    protected final FutureMarket market = new FutureMarket();
    private long purchaseId = 0l;
    private final Stock stock = new Stock();

    protected Properties properties;
    protected String shipperName;
    protected String sellerBaseAddr;
    private String name;
    private int purchaseTrigger, purchaseQuantity;
    final private String myBaseAddr;
    private String shipperBaseAddr;
    private String sellerName;
    private String role;

    public AbstractSupermarket() throws IOException {
        readProperties();
        setRole(sellerName);
        market.register(role, name);

        myBaseAddr = market.getMyBaseAddress(name);
        sellerBaseAddr = market.getBaseAddress(sellerName);
        if (!role.equals(Role.MANUFACTURER)) {
            shipperBaseAddr = market.getBaseAddress(shipperName);
        }

        stock.loadProducts(properties, PRODUCTS);
    }

    abstract protected void buy() throws IOException;

    abstract public Purchase purchase(ShopList list, CustomerInfo customer) throws IOException;

    @Override
    public ShopList getPrices(final ShopList shopList) {
        double price;
        Product product;

        for (ShopListItem item : shopList.getItems()) {
            product = item.getProduct();
            price = getPrice(product);
            product.setPrice(price);

            item.setSellerEndpoint(myBaseAddr);
        }
        return shopList;
    }

    protected Purchase getFromStock(final ShopList list, final CustomerInfo customer)
            throws IOException {
        // Manufacturers have infinite resources
        if (!role.equals(Role.MANUFACTURER)) {
            removeFromStock(list);
            supplyStock();
        }

        final Purchase purchase = new Purchase(getPurchaseId(), myBaseAddr, shipperBaseAddr, list,
                customer);

        return purchase;
    }

    @Override
    public void reset() {
        stock.reset();
        stock.loadProducts(properties, PRODUCTS);
    }

    protected CustomerInfo getCostumerInfo() throws UnknownHostException {
        final CustomerInfo customer = new CustomerInfo();
        customer.setName(name);
        customer.setCreditCard("Visa 123 456");
        customer.setAddress("ZIP 12345-678");

        return customer;
    }

    private void readProperties() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream propFile = loader.getResourceAsStream("supermarket.properties");
        properties = new Properties();
        properties.load(propFile);

        name = properties.getProperty("name");
        purchaseTrigger = Integer.parseInt(properties.getProperty("purchase.trigger"));
        purchaseQuantity = Integer.parseInt(properties.getProperty("purchase.quantity"));
        sellerName = properties.getProperty("seller.name", "");
        shipperName = properties.getProperty("shipper.name");
    }

    private void setRole(final String sellerName) {
        role = sellerName.isEmpty() ? Role.MANUFACTURER : Role.SUPERMARKET;
    }

    private double getPrice(final Product product) {
        final Product myProduct = stock.search(product);
        return myProduct.getPrice();
    }

    private long getPurchaseId() {
        synchronized (this) {
            purchaseId++;
        }

        return purchaseId;
    }

    private void supplyStock() throws IOException {
        final List<Product> productsToBuy = stock.getProductsLowInStock(purchaseTrigger);
        for (Product product : productsToBuy) {
            addToShopList(product);
        }

        if (!shopList.isEmpty()) {
            buy();
            addPurchaseToStock();
            shopList.clear();
        }
    }

    private void addPurchaseToStock() {
        for (Product product : shopList.getProducts()) {
            stock.add(product, purchaseQuantity);
        }
    }

    private void addToShopList(final Product product) {
        final ShopListItem item = new ShopListItem(product);

        item.setProduct(product);
        item.setQuantity(purchaseQuantity);
        item.setSellerEndpoint(sellerBaseAddr);

        shopList.put(item);
    }

    private void removeFromStock(final ShopList shopList) {
        for (ShopListItem item : shopList.getItems()) {
            stock.remove(item.getProduct(), item.getQuantity());
        }
    }
}