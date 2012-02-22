package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;

public abstract class SupermarketImpl implements Supermarket {

    protected HashMap<String, Double> priceTable = new HashMap<String, Double>();
    private long currentId = 1l;
    private FutureMarket futureMarket;
    private static String WSDL;
    private static Shipper shipper;

    public SupermarketImpl(final int supermarketNumber) {
        futureMarket = new FutureMarket();

        final String relPath = getRelativePath(supermarketNumber);
        futureMarket.register(FutureMarket.SUPERMARKET_ROLE, relPath);
        WSDL = futureMarket.getMyWsdl(relPath);

        shipper = futureMarket.getFirstClient(FutureMarket.SHIPPER_ROLE,
                FutureMarket.SHIPPER_SERVICE, Shipper.class);

        this.registerProducts();
    }

    protected abstract void registerProducts();

    public String getWsdl() {
        return WSDL;
    }

    private synchronized long getListId() {
        return currentId++;
    }

    private String getRelativePath(final int supermarketNumber) {
        String path = "supermarket" + supermarketNumber;
        path = path + "/" + path;

        return path;
    }

    @WebMethod
    public ProductPrice[] getPrices(String[] products) {
        List<ProductPrice> productPriceList = new ArrayList<ProductPrice>();
        for (String product : products) {
            Double price = priceTable.get(product);
            if (price != null) {
                productPriceList.add(new ProductPrice(product, price));
            }
        }

        return productPriceList.toArray(new ProductPrice[1]);
    }

    @WebMethod
    public PurchaseInfo purchase(final String[] products, final CustomerInfo customerInfo) {
        final PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setCustomerInfo(customerInfo);
        purchaseInfo.setProducts(products);
        purchaseInfo.setValue(10.0);
        purchaseInfo.setId("" + getListId());
        purchaseInfo.setSellerEndpoint(WSDL);

        shipper.setDelivery(purchaseInfo);

        return purchaseInfo;
    }
}