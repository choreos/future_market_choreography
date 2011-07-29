package eu.choreos.services;

import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class PaoDoFuturoWS {
	
	@WebMethod
	public double getProductPriceByName(String name){
		HashMap<String, Double> priceTable = new HashMap<String, Double>();
		
		priceTable.put("milk", 3.96);
		priceTable.put("cereal", 27.11);
		priceTable.put("rice", 4.76);
		priceTable.put("popcorn", 2.18);
		
		return priceTable.get(name);
	}

}
