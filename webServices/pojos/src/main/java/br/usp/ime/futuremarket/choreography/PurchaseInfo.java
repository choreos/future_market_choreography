package br.usp.ime.futuremarket.choreography;

import java.util.Set;

public class PurchaseInfo {

    private String id;

    private String sellerEndpoint;
    
    private String shipperName;

    private Set<ProductQuantity> products;

    private Double value;

    private CustomerInfo customerInfo;

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

    public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public Set<ProductQuantity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductQuantity> products) {
        this.products = products;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
