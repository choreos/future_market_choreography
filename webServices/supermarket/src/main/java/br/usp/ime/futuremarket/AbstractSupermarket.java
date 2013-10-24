package br.usp.ime.futuremarket;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import javax.jws.WebMethod;

import br.usp.ime.futuremarket.choreography.WSInfo;

public abstract class AbstractSupermarket extends EnactmentEngineImpl implements Supermarket {
    private static final int PRODUCTS = 10;

    protected final ShopList shopList = new ShopList();
    private final AtomicLong purchaseId = new AtomicLong(0);
    private final Stock stock = new Stock();

    private static final Properties PROP = new Properties();
    private final int purchaseTrigger, purchaseQuantity;
    private Role role;
    private String myBaseAddr, shipperBaseAddr, sellerBaseAddr;

    public AbstractSupermarket(final AbstractFutureMarket market) throws IOException,
            InterruptedException {
        super(getServiceName(), market);

        purchaseTrigger = Integer.parseInt(PROP.getProperty("purchase.trigger"));
        purchaseQuantity = Integer.parseInt(PROP.getProperty("purchase.quantity"));

        stock.loadProducts(PROP, PRODUCTS);
    }

    abstract protected void buy() throws IOException;

    abstract public Purchase purchase(ShopList list, CustomerInfo customer) throws IOException;

    abstract protected AbstractWSInfo getWSInfo();

    @Override
    @WebMethod
    public String setInvocationAddress(final String role, final String name, final List<String> registryEndpoints)
            throws IOException {
        super.setInvocationAddress(role, name, registryEndpoints);

        myBaseAddr = market.getMyBaseAddress(serviceName);
        this.role = getRole(myBaseAddr);

        return "OK";
    }

    private Role getRole(final String myBaseAddr) {
        final AbstractWSInfo info = new WSInfo();
        info.setBaseAddress(myBaseAddr);
        return info.getRole();
    }

    // TODO Synchronized setter
    protected String getShipperBaseAddress() throws IOException {
        if (shipperBaseAddr == null) {
            final String name = PROP.getProperty("shipper.name");
            shipperBaseAddr = market.getBaseAddress(name);
        }
        return shipperBaseAddr;
    }

    // TODO Synchronized setter
    protected String getSellerBaseAddress() throws IOException {
        if (sellerBaseAddr == null) {
            final String name = PROP.getProperty("seller.name");
            sellerBaseAddr = market.getBaseAddress(name);
        }
        return sellerBaseAddr;
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

        return new Purchase(purchaseId.getAndIncrement(), getShipperBaseAddress(), list, customer);
    }

    @Override
    public void reset() throws IOException, InterruptedException {
        synchronized (stock) {
            stock.reset();
            stock.loadProducts(PROP, PRODUCTS);
            shopList.clear();
        }
        shipperBaseAddr = null;
        sellerBaseAddr = null;
    }

    protected CustomerInfo getCostumerInfo() throws UnknownHostException {
        final CustomerInfo customer = new CustomerInfo();
        customer.setName(serviceName);
        customer.setCreditCard("Visa 123 456");
        customer.setAddress("ZIP 12345-678");

        return customer;
    }

    private static String getServiceName() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream propFile = loader.getResourceAsStream("supermarket.properties");
        PROP.load(propFile);
        propFile.close();

        return PROP.getProperty("name");
    }

    private double getPrice(final Product product) {
        final Product myProduct = stock.search(product);
        return myProduct.getPrice();
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
        item.setSeller(getSellerBaseAddress());

        shopList.put(item);
    }

    private void removeFromStock(final ShopList shopList) {
        for (ShopListItem item : shopList.getShopListItems()) {
            stock.remove(item.getProduct(), item.getQuantity());
        }
    }
}