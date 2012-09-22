package br.usp.ime.futuremarket;

import java.util.HashMap;
import java.util.Map;

public class ProductList {
	private Map<String, Integer> items = new HashMap<String, Integer>();
	
	public ProductList(){
		
	}
	
	public ProductList(String productName) {
		put(productName, 1);
	}
	
	public Integer put(String productName, int quantity) {
		return items.put(productName, quantity);
	}
	
	public Integer get(String productName) {
		return items.get(productName);
	}

	public Map<String, Integer> getItems() {
		return items;
	}

	public void setItems(Map<String, Integer> items) {
		this.items = items;
	}
	
	
}
