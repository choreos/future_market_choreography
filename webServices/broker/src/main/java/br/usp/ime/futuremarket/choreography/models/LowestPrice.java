package br.usp.ime.futuremarket.choreography.models;

public class LowestPrice {

	private String id;
	private Double price;
	
	public LowestPrice(){}

	public LowestPrice(String id, Double price) {
		this.id = id;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
