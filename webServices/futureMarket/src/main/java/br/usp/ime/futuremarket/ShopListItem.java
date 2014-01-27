package br.usp.ime.futuremarket;

public class ShopListItem {
    private Product product;
    private int quantity = -1;
    private String seller; // Must be the seller name in this version

    public ShopListItem() {
        // Avoiding IllegalAnnotationExceptions
    }

    public ShopListItem(final Product product) {
        this.product = product;
    }

    public double getTotalPrice() {
        final double unitPrice = product.getPrice();
        double totalPrice;

        if (quantity < 0 || unitPrice < 0) {
            totalPrice = -1;
        } else {
            totalPrice = unitPrice * quantity;
        }

        return totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(final String seller) {
        this.seller = seller;
    }
}