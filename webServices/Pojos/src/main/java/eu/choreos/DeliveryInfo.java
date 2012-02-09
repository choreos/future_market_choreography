package eu.choreos;

import java.util.Calendar;
import java.util.Date;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;

public class DeliveryInfo {
	
	private String id;
	
	private String date;
	
	private PurchaseInfo purchase;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public PurchaseInfo getPurchase() {
		return purchase;
	}

	public void setPurchase(PurchaseInfo purchase) {
		this.purchase = purchase;
	}
	
	public Item getItem(String tagName) {
		Item item = new ItemImpl(tagName);
		
		Item i = new ItemImpl("date");
		i.setContent(date);
		item.addChild(i);
		
		i = new ItemImpl("id");
		i.setContent(id);
		item.addChild(i);
		
		item.addChild(purchase.getItem("purchase"));
		
		return item;
	}
	
}
