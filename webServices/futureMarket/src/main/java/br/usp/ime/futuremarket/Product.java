package br.usp.ime.futuremarket;

public class Product {
    private String name;
    private double price = Double.MAX_VALUE;

    public Product() {
        // Avoiding IllegalAnnotationExceptions
    }

    @Override
    public boolean equals(final Object obj) {
        final Product anotherProduct = (Product) obj;
        return name.equals(anotherProduct.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Product(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }
}