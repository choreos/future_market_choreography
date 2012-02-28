package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
endpointInterface = "br.usp.ime.futuremarket.Supermarket")
public class SupermarketImpl implements Supermarket {

    protected HashMap<String, Double> priceTable;
    protected HashMap<String, Integer> stockItems;
    private long currentId;
    private FutureMarket futureMarket;
    private static String WSDL;
    private static Shipper shipper;
    private String shipperName;
    
    private ClassLoader loader = SupermarketImpl.class.getClassLoader();
    private Properties properties;
    

    public SupermarketImpl() {
        futureMarket = new FutureMarket();
        priceTable = new HashMap<String, Double>();
        stockItems = new HashMap<String, Integer>();
        currentId = 0l;
        properties = new Properties();

        try {
            properties.load(loader.getResourceAsStream("supermarket.properties"));
        } catch (IOException e) {
            System.out.println("Could not read resources/supermarket.properties");
        }

        final String supermarketNumber = properties.getProperty("supermarketNumber");
        final String relPath = getRelativePath(supermarketNumber);
        futureMarket.register(FutureMarket.SUPERMARKET_ROLE, "supermarket"+supermarketNumber, relPath);
        WSDL = futureMarket.getMyWsdl(relPath);

        shipperName = properties.getProperty("shipper.name");
        if (shipperName == null) shipperName = "Shipper";
        shipper = futureMarket.getClientByName(shipperName, FutureMarket.SHIPPER_SERVICE, Shipper.class);

        this.registerProducts();
    }

    public String getWsdl() {
        return WSDL;
    }

    private long getListId() {
        synchronized (this) {
            currentId++;
        }

        return currentId;
    }

    private String getRelativePath(final String supermarketNumber) {
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
        purchaseInfo.setShipperName(shipperName);

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
    
    private void registerProducts(){
        for(int i=1; i<=10; i++){
        	// product<i>.price=x
        	// product<i>.stockItems=y
        	priceTable.put("product"+i, Double.parseDouble(properties.getProperty("product"+i+".price")));
        	stockItems.put("product"+i, Integer.parseInt(properties.getProperty("product"+i+".stock")));
        }
    }
}
