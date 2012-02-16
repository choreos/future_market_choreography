package eu.choreos;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;

public class DeliveryInfo {
	
	private String id;
	
	private String date;
	
	private String status;
	
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		
		i = new ItemImpl("status");
		i.setContent(status);
		item.addChild(i);
		
		item.addChild(purchase.getItem("purchase"));
		
		return item;
	}
	
	public static DeliveryInfo fromItem(Item i){
		DeliveryInfo d = new DeliveryInfo();
		try {
			d.setDate(i.getChild("date").getContent());
			d.setId(i.getChild("id").getContent());
			d.setStatus(i.getChild("status").getContent());
			d.setPurchase(PurchaseInfo.fromItem(i.getChild("purchase")));
		} catch (NoSuchFieldException e) {
			d = null;
			e.printStackTrace();
		}
		return d;
	}
	
}
