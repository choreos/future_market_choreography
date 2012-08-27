package br.usp.ime.futuremarket;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

public abstract class AbstractSupermarket implements Supermarket {
    private static final int PRODUCTS = 10;

    protected final ShopList shopList = new ShopList();
    protected final AbstractFutureMarket market;
    private long purchaseId = 0l;
    private final Stock stock = new Stock();

    private final Properties properties;
    private String myName;
    private String myBaseAddr, shipperBaseAddr, sellerBaseAddr;
    private int purchaseTrigger, purchaseQuantity;
    private final Role role;

    public AbstractSupermarket() throws IOException {
        properties = readProperties();
        stock.loadProducts(properties, PRODUCTS);

        market = getFutureMarket();
        market.register(myName);
        myBaseAddr = market.getMyBaseAddress(myName);

        role = getRole(myName);
    }

    abstract protected void buy() throws IOException;

    abstract public Purchase purchase(ShopList list, CustomerInfo customer) throws IOException;

    abstract protected AbstractWSInfo getWSInfo();

    abstract protected AbstractFutureMarket getFutureMarket();

    private Role getRole(String name) {
        final AbstractWSInfo info = getWSInfo();
        info.setName(name);
        return info.getRole();
    }

    @Override
    public ShopList getPrices(final ShopList shopList) {
        double price;
        Product product;

        for (ShopListItem item : shopList.getShopListItems()) {
            product = item.getProduct();
            price = getPrice(product);
            product.setPrice(price);
            item.setSeller(myBaseAddr);
        }

        return shopList;
    }

    protected Purchase getFromStock(final ShopList list, final CustomerInfo customer)
            throws IOException {
        // Manufacturers have infinite resources
        if (!Role.MANUFACTURER.equals(role)) {
            synchronized (stock) {
                removeFromStock(list);
                supplyStock();
            }
        }

        return new Purchase(getPurchaseId(), getShipperBaseAddr(), list, customer);
    }

    @Override
    public void reset() {
        synchronized (stock) {
            stock.reset();
            stock.loadProducts(properties, PRODUCTS);
            shopList.clear();
        }
        synchronized (this) {
            sellerBaseAddr = null;
            shipperBaseAddr = null;
        }
    }

    protected CustomerInfo getCostumerInfo() throws UnknownHostException {
        final CustomerInfo customer = new CustomerInfo();
        customer.setName(myName);
        customer.setCreditCard("Visa 123 456");
        customer.setAddress("ZIP 12345-678");

        return customer;
    }

    protected String getShipperBaseAddr() throws IOException {
        synchronized (this) {
            if (shipperBaseAddr == null) {
                final String shipperName = properties.getProperty("shipper.name");
                shipperBaseAddr = market.getBaseAddress(shipperName);
            }
        }
        return shipperBaseAddr;
    }

    protected String getSellerBaseAddr() throws IOException {
        synchronized (this) {
            if (sellerBaseAddr == null) {
                final String sellerName = properties.getProperty("seller.name");
                sellerBaseAddr = market.getBaseAddress(sellerName);
            }
        }
        return sellerBaseAddr;
    }

    private Properties readProperties() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream propFile = loader.getResourceAsStream("supermarket.properties");
        final Properties properties = new Properties();
        properties.load(propFile);
        propFile.close();

        myName = properties.getProperty("name");
        purchaseTrigger = Integer.parseInt(properties.getProperty("purchase.trigger"));
        purchaseQuantity = Integer.parseInt(properties.getProperty("purchase.quantity"));

        return properties;
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

    private void addToShopList(final Product product) throws IOException {
        final ShopListItem item = new ShopListItem(product);

        item.setProduct(product);
        item.setQuantity(purchaseQuantity);
        item.setSeller(getSellerBaseAddr());

        shopList.put(item);
    }

    private void removeFromStock(final ShopList shopList) {
        for (ShopListItem item : shopList.getShopListItems()) {
            stock.remove(item.getProduct(), item.getQuantity());
        }
    }
}