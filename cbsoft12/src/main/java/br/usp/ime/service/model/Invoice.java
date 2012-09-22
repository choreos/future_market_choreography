package br.usp.ime.service.model;

public class Invoice {

	private String productName;
	private String customerName;
	private String paymentStatus;

	public Invoice() {
	}

	public Invoice(String productName, String customerName,
			String paymentStatusMessage) {
		this.productName = productName;
		this.customerName = customerName;
		this.paymentStatus = paymentStatusMessage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
