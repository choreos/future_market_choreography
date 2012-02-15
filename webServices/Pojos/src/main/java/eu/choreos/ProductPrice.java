package eu.choreos;

import eu.choreos.vv.clientgenerator.Item;

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

	public ProductPrice(Item item){
		try {
			this.product = item.getChild("product").getContent();
			this.price = Double.parseDouble(item.getChild("price").getContent());
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
