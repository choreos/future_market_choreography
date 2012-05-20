package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Registry")
public class RegistryImpl implements Registry {

    private HashMap<String, Set<String>> endpoints;
    private HashMap<String, String> names;

    private static int orchRobin = -1;

    public RegistryImpl() {
        endpoints = new HashMap<String, Set<String>>();
        names = new HashMap<String, String>();
    }

    @Override
    public List<String> getList(String role) {
        if (endpoints.containsKey(role))
            return (List<String>) (new ArrayList<String>(endpoints.get(role)));
        else
            return new ArrayList<String>();
    }

    @Override
    public String getFirst(String role) {
    	
    	if (role.equals(FutureMarket.ORCHESTRATOR_ROLE))
    		return getOrchestratorWsdl();
    	
        if (endpoints.containsKey(role))
            return (String) endpoints.get(role).iterator().next();
        else
            return "";
    }

    @Override
    public String add(String role, String name, String endpoint) {
        if (!endpoints.containsKey(role))
            endpoints.put(role, new HashSet<String>());
        endpoints.get(role).add(endpoint);
        names.put(name, endpoint);

        return "OK";
    }

    @Override
    public String remove(String role, String name) {
    	if (names.containsKey(name)){
    		String endpoint = names.get(name);

    		if (endpoints.containsKey(role)) {
    			Set<String> roleEndpoints = endpoints.get(role);
    			if (roleEndpoints.contains(endpoint)) {
    				roleEndpoints.remove(endpoint);
    			}
    		}
    		
    		names.remove(name);
    		return "OK";
    	} else
    		return "Endpoint not found";
    }

	@Override
	public String getServiceEndpoint(String name) {
		if (names.containsKey(name)){
			return names.get(name);
		}
		return "";
	}
	
    private String getOrchestratorWsdl() {
    	
    	List<String> wsdls = getList(FutureMarket.ORCHESTRATOR_ROLE);
    	int n = wsdls.size();
    	synchronized(RegistryImpl.class) {
    		orchRobin = (orchRobin + 1) % n;
    	}
    	String orchWsdl = wsdls.get(orchRobin);
    	System.out.println("Selected orchestration: " + orchWsdl);
    	return orchWsdl;
    }
    
}