package eu.choreos;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;

public class CustomerInfo {
	
	private String id;
	
	private String name;
	
	private String zipcode;
	
	private String endpoint;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public Item getItem(String tagName) {
		Item item = new ItemImpl(tagName);
		
		Item i = new ItemImpl("id");
		i.setContent(id);
		item.addChild(i);
		
		i = new ItemImpl("name");
		i.setContent(name);
		item.addChild(i);
		
		i = new ItemImpl("endpoint");
		i.setContent(endpoint);
		item.addChild(i);
		
		i = new ItemImpl("zipcode");
		i.setContent(zipcode);
		item.addChild(i);
		
		return item;
	}
	
}
