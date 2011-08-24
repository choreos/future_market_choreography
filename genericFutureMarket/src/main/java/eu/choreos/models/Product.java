package eu.choreos.models;

public class Product {
	private String name;
	private double price;

	public Product() {
		this.name = "";
		this.price = 0.0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
