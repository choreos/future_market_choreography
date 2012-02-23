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

    public RegistryImpl() {
        endpoints = new HashMap<String, Set<String>>();
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
        if (endpoints.containsKey(role))
            return (String) endpoints.get(role).iterator().next();
        else
            return "";
    }

    @Override
    public String add(String role, String endpoint) {
        if (!endpoints.containsKey(role))
            endpoints.put(role, new HashSet<String>());
        endpoints.get(role).add(endpoint);

        return "OK";
    }

    @Override
    public String remove(String role, String endpoint) {
        if (endpoints.containsKey(role)) {
            Set<String> roleEndpoints = endpoints.get(role);
            if (roleEndpoints.contains(endpoint)) {
                roleEndpoints.remove(endpoint);
                return "OK";
            } else
                return "Endpoint not found";
        } else
            return "Role not found";
    }
}