package br.usp.ime.service;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.usp.ime.service.model.Invoice;
import br.usp.ime.service.model.PersonalInfo;

@WebService
public class SM1WS {
	
	private static Map<String, String> items;
	
	public SM1WS(){
		items = new HashMap<String, String>();
		items.put("milk", "2.0");
		items.put("bread", "1.0");
		items.put("butter", "5.00");
		items.put("juice", "3.00");
		items.put("wine", "21.42");
		items.put("beer", "3.50");
		items.put("jam", "7.50");
		items.put("ham", "6.00");
		items.put("sugar", "4.0");
		items.put("salt", "5.00");		
	}

	@WebMethod
	@WebResult(name="result")
	public String checkPriceOf(String productName)  {
		return items.get(productName);
	}
	
	@WebMethod
	@WebResult(name="invoice")
	public Invoice purchase(@WebParam(name="info") PersonalInfo personalInfo, @WebParam(name="productName") String productName) {
		return new Invoice(productName, personalInfo.getName(), "Payment Authorized");
	}
	

}
