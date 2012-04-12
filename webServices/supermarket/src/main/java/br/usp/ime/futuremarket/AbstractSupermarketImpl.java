package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;

public abstract class AbstractSupermarketImpl implements Supermarket {

    protected HashMap<String, Double> priceTable;
    private long currentId;
    private FutureMarket futureMarket;
    private static String WSDL;
    private static Shipper shipper;
    
    private int smNumber;

    public AbstractSupermarketImpl(final int supermarketNumber) {
    	smNumber = supermarketNumber;
    	
        futureMarket = new FutureMarket();
        priceTable = new HashMap<String, Double>();
        currentId = 0l;

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

    private double getListId() {
    	return FutureMarket.nextID(Thread.currentThread());
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
        purchaseInfo.setValue(getTotalPrice(products));
        purchaseInfo.setId("" + getListId());
        purchaseInfo.setSellerEndpoint(WSDL);

        shipper.setDelivery(purchaseInfo);

        return purchaseInfo;
    }

    private Double getTotalPrice(String[] products) {
        Double total = 0d;

        for (String product : products) {
            total += priceTable.get(product);
        }

        return total;
    }
}