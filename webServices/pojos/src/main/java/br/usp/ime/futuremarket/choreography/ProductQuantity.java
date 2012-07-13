package br.usp.ime.futuremarket.choreography;

public class ProductQuantity {
	
	private String product;
	
	private Integer quantity;
	
	public ProductQuantity() {
		
	}

	public ProductQuantity(String product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}
