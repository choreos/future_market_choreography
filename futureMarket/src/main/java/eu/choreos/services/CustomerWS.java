package eu.choreos.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.WSClient;

@WebService
public class CustomerWS {
	
	List<String> endpoints;
	List<String> products;
	
	@WebMethod
	public List<String> setSupermarketsList(@WebParam(name="endpoint") List<String> endpoints){
		this.endpoints = endpoints;
			
		return endpoints;
	}
	
	@WebMethod
	public List<String> setProductsList(@WebParam(name="item") List<String> products){
		this.products = products;
		
		return products;
	}
	
	@WebMethod
	public double getLowestPriceForList() throws Exception{
		double totalPrice = 0.0;
		double lowestUnitPrice = 0.0;
		
		for(String product : products){
			lowestUnitPrice = Double.MAX_VALUE;
			
			for(String endpoint : endpoints){
				
				WSClient supermarket = new WSClient(endpoint);
				Item response = supermarket.request("searchForProduct", product);
				
				double price = response.getChild("return").getChild("price").getContentAsDouble();
				
				if (price <= lowestUnitPrice)
					lowestUnitPrice = price;
			}
			
			totalPrice += lowestUnitPrice;
		}
		
		return totalPrice;
	}

}
