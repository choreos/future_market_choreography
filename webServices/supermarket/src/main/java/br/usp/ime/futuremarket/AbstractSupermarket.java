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

    private Properties properties;
    private String shipperBaseAddr;
    private String sellerBaseAddr;
    private String name;
    private int purchaseTrigger, purchaseQuantity;
    final private String myBaseAddr;
    private final String role;

    public AbstractSupermarket() throws IOException {
        readProperties();
        role = properties.getProperty("role");
        market.register(role, name);

        myBaseAddr = market.getMyBaseAddress(name);

        stock.loadProducts(properties, PRODUCTS);
    }

    abstract protected void buy() throws IOException;

    abstract public Purchase purchase(ShopList list, CustomerInfo customer) throws IOException;

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
        if (!role.equals(Role.MANUFACTURER)) {
            synchronized (this) {
                removeFromStock(list);
                supplyStock();
            }
        }

        return new Purchase(getPurchaseId(), getShipperBaseAddr(), list, customer);
    }

    @Override
    public void reset() {
        synchronized (this) {
            stock.reset();
            stock.loadProducts(properties, PRODUCTS);
        }
    }

    protected CustomerInfo getCostumerInfo() throws UnknownHostException {
        final CustomerInfo customer = new CustomerInfo();
        customer.setName(name);
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
                final String sellerName = properties.getProperty("seller.name", "");
                sellerBaseAddr = market.getBaseAddress(sellerName);
            }
        }
        return sellerBaseAddr;
    }

    private void readProperties() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream propFile = loader.getResourceAsStream("supermarket.properties");
        properties = new Properties();
        properties.load(propFile);

        name = properties.getProperty("name");
        purchaseTrigger = Integer.parseInt(properties.getProperty("purchase.trigger"));
        purchaseQuantity = Integer.parseInt(properties.getProperty("purchase.quantity"));
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