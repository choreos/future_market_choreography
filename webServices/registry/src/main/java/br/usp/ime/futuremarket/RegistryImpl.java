package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br", endpointInterface = "br.usp.ime.futuremarket.Registry")
public class RegistryImpl implements Registry {

    // HashMap<role, ArrayList<endpoint>>
    private HashMap<String, List<String>> endpoints;
    // HashMap<name, endpoint>
    private HashMap<String, String> names;

    public RegistryImpl() {
        endpoints = new HashMap<String, List<String>>();
        names = new HashMap<String, String>();
    }

    @Override
    public List<String> getList(String role) {
        if (endpoints.containsKey(role))
            return endpoints.get(role);
        else
            return new ArrayList<String>();
    }

    @Override
    public String getFirst(String role) {
        if (endpoints.containsKey(role))
            return endpoints.get(role).get(0);
        else
            return "";
    }

    @Override
    public String getByIndex(final String role, final int index) {
        String endpoint;

        if (!endpoints.containsKey(role) || endpoints.get(role).isEmpty()) {
            endpoint = "";
        } else {
            final List<String> roleEndpoints = endpoints.get(role);
            final int arrayIndex = index % roleEndpoints.size();
            endpoint = roleEndpoints.get(arrayIndex);
        }

        return endpoint;
    }

    @Override
    public String add(String role, String name, String endpoint) {
        if (!endpoints.containsKey(role))
            endpoints.put(role, new ArrayList<String>());

        final List<String> roleEndpoints = endpoints.get(role);
        if (!roleEndpoints.contains(endpoint)) {
            roleEndpoints.add(endpoint);
            names.put(name, endpoint);
        }

        return "OK";
    }

    @Override
    public String remove(String role, String name) {
        if (names.containsKey(name)) {
            String endpoint = names.get(name);

            if (endpoints.containsKey(role)) {
                endpoints.get(role).remove(endpoint);
            }

            names.remove(name);
            return "OK";
        } else
            return "Endpoint not found";
    }

    @Override
    public String getServiceEndpoint(String name) {
        if (names.containsKey(name)) {
            return names.get(name);
        }
        return "";
    }
}