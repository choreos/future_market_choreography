package eu.choreos.services;

import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class FutureMartWS {
	
	@WebMethod
	public double getPrice(String productName) {
		HashMap<String, Double> priceTable = new HashMap<String, Double>();
		priceTable.put("milk", 4.79);
		priceTable.put("cereal", 21.0);
		priceTable.put("rice", 2.67);
		priceTable.put("popcorn", 4.48);
		
		return priceTable.get(productName);
	}
}
