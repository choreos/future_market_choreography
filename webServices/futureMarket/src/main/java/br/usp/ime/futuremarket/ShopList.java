package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShopList {
    private Map<String, ShopListItem> items = new HashMap<String, ShopListItem>();;

    public ShopList() {
        // Avoiding IllegalAnnotationExceptions
    }

    public ShopList(final ShopListItem item) {
        this.put(item);
    }

    final public void put(final ShopListItem item) {
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

    /**
     * 
     * @return Seller base address of the first item or "".
     */
    public String getSeller() {
        final Iterator<ShopListItem> items = this.items.values().iterator();
        String seller;

        if (items.hasNext()) {
            final ShopListItem item = items.next();
            seller = item.getSeller();
        } else {
            seller = "";
        }

        return seller;
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

    public Collection<ShopListItem> getShopListItems() {
        return items.values();
    }

    public Map<String, ShopListItem> getItems() {
        return items;
    }

    public void setItems(final Map<String, ShopListItem> items) {
        this.items = items;
    }
}