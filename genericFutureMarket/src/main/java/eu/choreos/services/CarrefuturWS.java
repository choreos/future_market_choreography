package eu.choreos.services;

import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class CarrefuturWS {

	@WebMethod
	public double calculatePriceOf(String aProduct){
		HashMap<String, Double> priceTable = new HashMap<String, Double>();
		
		priceTable.put("milk", 3.95);
		priceTable.put("cereal", 21.50);
		priceTable.put("rice", 3.67);
		priceTable.put("popcorn", 5.18);
		
		return priceTable.get(aProduct);
		
	}
}