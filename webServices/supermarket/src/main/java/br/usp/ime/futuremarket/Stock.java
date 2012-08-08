package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Stock {
    private final Map<Product, Integer> products = new HashMap<Product, Integer>();

    public void loadProducts(final Properties properties, final int lastProductNumber) {
        String productName;
        double price;
        int quantity;

        for (int i = lastProductNumber; i > 0; i--) {
            productName = "product" + i;
            price = Double.parseDouble(properties.getProperty(productName + ".price"));
            quantity = Integer.parseInt(properties.getProperty(productName + ".stock"));

            addProduct(productName, price, quantity);
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
        final int quantity = products.get(product) + bought;
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
        final Product product = new Product(name, price);
        products.put(product, quantity);
    }
}