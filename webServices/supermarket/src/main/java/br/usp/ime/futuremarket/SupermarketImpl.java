package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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

    public SupermarketImpl() {
        futureMarket = new FutureMarket();
        priceTable = new HashMap<String, Double>();
        stockItems = new HashMap<String, Integer>();
        currentId = 0l;
        Properties properties = new Properties();

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

        this.registerProducts(properties);
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
    public ProductPrice[] getPrices(Set<ProductQuantity> products) {
        List<ProductPrice> productPriceList = new ArrayList<ProductPrice>();
        for (ProductQuantity product : products) {
            Double price = priceTable.get(product.getProduct());
            if (price != null) {
                productPriceList.add(new ProductPrice(product.getProduct(), price));
            }
        }

        return productPriceList.toArray(new ProductPrice[1]);
    }

    @WebMethod
    public PurchaseInfo purchase(final Set<ProductQuantity> products, final CustomerInfo customerInfo) {
        
    	final PurchaseInfo purchaseInfo = new PurchaseInfo();
    	
    	try {
        purchaseInfo.setCustomerInfo(customerInfo);
        purchaseInfo.setProducts(products);
        purchaseInfo.setValue(getTotalPrice(products));
        purchaseInfo.setId("" + getListId());
        purchaseInfo.setSellerEndpoint(WSDL);
        purchaseInfo.setShipperName(shipperName);

        shipper.setDelivery(purchaseInfo);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}

        return purchaseInfo;
    }

    private Double getTotalPrice(Set<ProductQuantity> products) {
        Double total = 0d;

        for (ProductQuantity product : products) {
            total += priceTable.get(product.getProduct()) * product.getQuantity();
        }

        return total;
    }
    
    private void registerProducts(Properties properties){
    	System.out.println(getWsdl() + "=============================================");
        for(int i=1; i<=10; i++){
        	// product<i>.price=x
        	// product<i>.stock=y
        	String product = "product"+i;
        	Double price = Double.parseDouble(properties.getProperty("product"+i+".price"));
        	Integer stock = Integer.parseInt(properties.getProperty("product"+i+".stock"));
        	priceTable.put(product, price);
        	stockItems.put(product, stock);
        	
        	System.out.println(product + " costs " + priceTable.get(product));
        }
    }
}
