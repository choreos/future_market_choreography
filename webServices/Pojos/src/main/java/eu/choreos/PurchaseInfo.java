package eu.choreos;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;

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

	public Item getItem(String tagName) {
		Item item = new ItemImpl(tagName);

		Item i = new ItemImpl("id");
		i.setContent(id);
		item.addChild(i);

		i = new ItemImpl("sellerEndpoint");
		i.setContent(sellerEndpoint);
		item.addChild(i);

		i = new ItemImpl("value");
		i.setContent(value.toString());
		item.addChild(i);

		for (String p : products) {
			i = new ItemImpl("products");
			i.setContent(p);
			item.addChild(i);
		}

		item.addChild(customer.getItem("customerInfo"));

		return item;
	}

	public static PurchaseInfo fromItem(Item item){
		PurchaseInfo purchaseInfo= new PurchaseInfo();
		try {
			purchaseInfo.setId(item.getChild("id").getContent());
			purchaseInfo.setSellerEndpoint(item.getChild("sellerEndpoint").getContent());
			List<String> products = new ArrayList<String>();
			for(Item i: item.getChildAsList("products")) {
				products.add(i.getContent());
			}
			purchaseInfo.setProducts(products);
			purchaseInfo.setValue(Double.parseDouble(item.getChild("value").getContent()));
			purchaseInfo.setCustomerInfo(CustomerInfo.fromItem(item.getChild("customerInfo")));
		} catch(Exception e){
			e.printStackTrace();
			purchaseInfo= null;
		}
		return purchaseInfo;
		}
}
