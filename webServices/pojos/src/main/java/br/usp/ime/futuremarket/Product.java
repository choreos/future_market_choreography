package br.usp.ime.futuremarket;

public class Product implements Comparable<Product> {
    private String name;
    private double price = -1;
    
    @Override
    public int compareTo(final Product anotherProduct) {
        return name.compareTo(anotherProduct.getName());
    }

    public Product() {
        // Avoiding IllegalAnnotationExceptions
    }

    public Product(final String name, final double price) {
        this.name = name;
        this.price = price;
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