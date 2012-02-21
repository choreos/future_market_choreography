package eu.choreos;

import java.util.List;

public class PurchaseInfo {

	private String id;

	private String sellerEndpoint;

	private String[] products;

	private Double value;

	private CustomerInfo customer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSellerEndpoint() {
		return sellerEndpoint;
	}

	public void setSellerEndpoint(String sellerEndpoint) {
		this.sellerEndpoint = sellerEndpoint;
	}

	public String[] getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products.toArray(new String[1]);
	}

	public void setProducts(String[] products) {
		this.products = products;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public CustomerInfo getCustomerInfo() {
		return customer;
	}

	public void setCustomerInfo(CustomerInfo customer) {
		this.customer = customer;
	}

}
