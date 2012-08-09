package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopList {
    private Map<String, ShopListItem> items;

    public ShopList() {
        items = new HashMap<String, ShopListItem>();
    }

    public void put(final ShopListItem item) {
        final String productName = item.getProduct().getName();
        items.put(productName, item);
    }

    public ShopListItem getItem(final Product product) {
        return items.get(product.getName());
    }
    
    public List<Product> getProducts() {
        final List<Product> products = new ArrayList<Product>();
        
        for (ShopListItem item : items.values()) {
            products.add(item.getProduct());
        }
        
        return products;
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public void clear() {
        items.clear();
    }

    public double getPrice() {
        double itemPrice;
        double price = 0;
        
        for (ShopListItem item : items.values()) {
            itemPrice = item.getTotalPrice();
            if (itemPrice < 0) {
                price = -1;
                break;
            } else {
                price += itemPrice;
            }

        }
        return price;
    }

    public Collection<ShopListItem> getItems() {
        return items.values();
    }

    public void setItems(final Map<String, ShopListItem> items) {
        this.items = items;
    }
}