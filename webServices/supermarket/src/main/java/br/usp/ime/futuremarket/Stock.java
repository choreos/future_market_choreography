package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Stock {
    private final Map<Product, Integer> products = new HashMap<Product, Integer>();
    
    public Map<String, String> getRealProducts(){
    	Map<String, String> products = new HashMap<String, String>();
    	
    	products.put("product1", "milk");
    	products.put("product2", "bread");
    	products.put("product3", "butter");
    	products.put("product4", "juice");
    	products.put("product5", "wine");
    	products.put("product6", "beer");
    	products.put("product7", "jam");
    	products.put("product8", "ham");
    	products.put("product9", "sugar");
    	products.put("product10", "salt");
    	
    	return products;
    }

    public void loadProducts(final Properties properties, final int lastProductNumber) {
        String productName;
        double price;
        int quantity;

        for (int i = lastProductNumber; i > 0; i--) {
        	Map<String, String> realProducts = getRealProducts();
        	
            productName = "product" + i;
            price = Double.parseDouble(properties.getProperty(realProducts.get(productName) + ".price"));
            quantity = Integer.parseInt(properties.getProperty(realProducts.get(productName) + ".stock"));

            addProduct(realProducts.get(productName), price, quantity);
        }
    }

    public Product search(final Product product) {
        Product result = null;

        if (products.containsKey(product)) {
            final Set<Product> myProducts = products.keySet();

            for (Product candidate : myProducts) {
                if (candidate.equals(product)) {
                    result = candidate;
                    break;
                }
            }
        }
        return result;
    }

    // If someone buys more than we have, the full amount is sold and the stock
    // is emptied
    public void remove(final Product product, final int sold) {
        final int current = products.get(product);
        final int toBeRemoved = (sold < current) ? sold : current;
        products.put(product, current - toBeRemoved);
    }

    public void add(final Product product, final int bought) {
        final int current = products.containsKey(product) ? products.get(product) : 0;
        final int quantity = current + bought;
        products.put(product, quantity);
    }

    public List<Product> getProductsLowInStock(final int purchaseTrigger) {
        final List<Product> result = new ArrayList<Product>();

        int quantity;
        for (Product product : products.keySet()) {
            quantity = products.get(product);
            if (quantity <= purchaseTrigger) {
                result.add(product);
            }
        }
        return result;
    }

    public void reset() {
        products.clear();
    }

    private void addProduct(final String name, final double price, final int quantity) {
        final Product product = new Product(name);
        product.setPrice(price);
        products.put(product, quantity);
    }
}