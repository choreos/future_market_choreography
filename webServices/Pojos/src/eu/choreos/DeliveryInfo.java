package eu.choreos;

import java.util.Calendar;

public class DeliveryInfo {
	
	private String id;
	
	private Calendar date;
	
	private PurchaseInfo purchase;
	
	private CustomerInfo customer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public PurchaseInfo getPurchase() {
		return purchase;
	}

	public void setPurchase(PurchaseInfo purchase) {
		this.purchase = purchase;
	}

	public CustomerInfo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerInfo customer) {
		this.customer = customer;
	}
	
	

}
