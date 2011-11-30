package eu.choreos.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.swing.JOptionPane;

import eu.choreos.models.CheapestProduct;
import eu.choreos.models.Order;
import eu.choreos.vv.actions.Rehearsal;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

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
	
	@WebMethod
	public String makePurchase(String id, String name, String address, String zipcode) throws Exception{
		String listOfShipper = "";
		
		for (String endpoint : endpoints) {
			
			WSClient supermarket = new WSClient(endpoint);
						
			Item request = new ItemImpl("purchase");
			Item orderID = new ItemImpl("id");
			orderID.setContent(id);
			request.addChild(orderID);
			
			Item personalData = getPersonalData(name, address, zipcode);
			request.addChild(personalData);
			
			Item response = supermarket.request("purchase", request);
			
			listOfShipper = response.getChild("confirmation").getContent();

		}
		
		return listOfShipper;	
	}
	
	private Item getPersonalData(String wName, String wAddress, String wZipcode) {
		Item personalData = new ItemImpl("data");
		
		Item name = new ItemImpl("name");
		name.setContent(wName);
		personalData.addChild(name);
		
		Item address = new ItemImpl("address");
		address.setContent(wAddress);
		personalData.addChild(address);
		
		Item zipCode = new ItemImpl("zipcode");
		zipCode.setContent(wZipcode);
		personalData.addChild(zipCode);
		return personalData;
	}

}
