package eu.choreos.services;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class SMRegistryWS {
	
	private List<String> supermarkets;
	
	public SMRegistryWS(){
		supermarkets = new ArrayList<String>();
	}
	
	@WebMethod
	public List<String> getList(){
		return supermarkets;
	}
	
	@WebMethod
	public void addSupermarket(String endpoint){
		supermarkets.add(endpoint);
	}

	
}
