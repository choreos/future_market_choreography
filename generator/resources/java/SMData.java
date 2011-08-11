package eu.choreos.services;

import java.util.HashMap;

public class SM#{id}Data {
	
	public static double getPrice(String name) {
		HashMap<String, Double> priceTable = new HashMap<String, Double>();
		
		priceTable.put("milk", 3.95);
		priceTable.put("cereal", 21.50);
		priceTable.put("rice", 3.67);
		priceTable.put("popcorn", 5.18);
		
		return priceTable.get(name);
	}

}
