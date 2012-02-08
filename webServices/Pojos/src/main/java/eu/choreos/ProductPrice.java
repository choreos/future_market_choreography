package eu.choreos;

public class ProductPrice {
	
	private String product;
	private Double price;
	
	public ProductPrice(){
	    
	}
	
	public ProductPrice(String product,Double price){
	    this.product = product;
	    this.price = price;
	}
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}


}
