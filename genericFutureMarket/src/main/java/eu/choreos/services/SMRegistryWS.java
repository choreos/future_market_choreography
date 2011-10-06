package eu.choreos.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace="http://smregistry.choreos.eu")
public class SMRegistryWS {
	
	private Set<String> supermarkets;
	
	public SMRegistryWS(){
		supermarkets = new HashSet<String>();
	}
	
	@WebMethod
	public List<String> getList(){
		return (List<String>) (new ArrayList<String>(supermarkets));
	}
	
	@WebMethod
	public String addSupermarket(String endpoint){
		supermarkets.add(endpoint);
		
		return "OK";
	}

	
}
