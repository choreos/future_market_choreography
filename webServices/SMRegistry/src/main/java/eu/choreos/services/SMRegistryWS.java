package eu.choreos.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://smregistry.choreos.eu")
public class SMRegistryWS {
	
	private HashMap<String, Set<String>> endpoints;
	
	public SMRegistryWS(){
		endpoints = new HashMap<String, Set<String>>();
	}

	@WebMethod
	public List<String> getList(String role){
		if (endpoints.containsKey(role))
			return (List<String>) (new ArrayList<String>(endpoints.get(role)));
		else
			return new ArrayList<String>();
	}
	
	@WebMethod
	public String getFirst(String role){
		if (endpoints.containsKey(role))
			return (String) endpoints.get(role).iterator().next();
		else
			return "";
	}

	@WebMethod
	public String add(String role, String endpoint){
		if (!endpoints.containsKey(role))
			endpoints.put(role, new HashSet<String>());
		endpoints.get(role).add(endpoint);
		
		return "OK";
	}

	@WebMethod
	public String remove(String role, String endpoint){
		if(endpoints.containsKey(role)) {
			Set<String> roleEndpoints = endpoints.get(role); 
			if (roleEndpoints.contains(endpoint)) {
				roleEndpoints.remove(endpoint);
				return "OK";
			} else
				return "Endpoint not found";
		}
		else
			return "Role not found";
			
	}
	
	public static void main(String[] a) {
		SMRegistryWS reg = new SMRegistryWS();
		System.out.println(reg.getList("r1"));
		reg.add("r1", "ep1");
		reg.add("r2", "ep2");
		reg.add("r1", "ep1");
		System.out.println(reg.getList("r1"));
		System.out.println(reg.getList("r2"));
		System.out.println(reg.remove("r1", "ep3"));
		System.out.println(reg.getList("r1"));
		System.out.println(reg.remove("r1", "ep3"));
		System.out.println(reg.remove("r3", "ep3"));
	}
	
}
