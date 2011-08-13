package eu.choreos.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import eu.choreos.vv.Item;
import eu.choreos.vv.WSClient;

@WebService
public class CustomerWS {
	
	private List<String> endpoints;
	private List<String> products;
	private HashMap<Integer, List<CheapestProduct>> orders;
	private int currentOrder;
	
	
	public CustomerWS(){
		currentOrder = 0;
		orders = new HashMap<Integer, List<CheapestProduct>>();
	}
	
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
	public Order getLowestPriceForList() throws Exception{
		double totalPrice = 0.0;
		double lowestUnitPrice = 0.0;
		List<CheapestProduct> items = new ArrayList<CheapestProduct>();
		String endpointChosen = "";
		
		for(String product : products){
			lowestUnitPrice = Double.MAX_VALUE;
			
			for(String endpoint : endpoints){
				
				WSClient supermarket = new WSClient(endpoint);
				Item response = supermarket.request("searchForProduct", product);
				
				double price = response.getChild("return").getChild("price").getContentAsDouble();
				
				if (price <= lowestUnitPrice){
					lowestUnitPrice = price;
					endpointChosen = endpoint;
				}
			}
			
			CheapestProduct cheapProd = new CheapestProduct();
			cheapProd.setName(product);
			cheapProd.setPrice(lowestUnitPrice);
			cheapProd.setSmEndpoint(endpointChosen);
			
			items.add(cheapProd);
			totalPrice += lowestUnitPrice;
		}
		
		currentOrder++;
		orders.put(currentOrder, items);
		
		Order order = new Order();
		order.setId(currentOrder);
		order.setPrice(totalPrice);
		
		return order;
	}

}
