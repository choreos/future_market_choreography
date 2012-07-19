package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Registry")
public class RegistryImpl implements Registry {
    // HashMap<role, ArrayList<endpoint>>
    private final Map<String, List<String>> services;
    // HashMap<name, endpoint>
    private final Map<String, String> names;

    public RegistryImpl() {
        services = new HashMap<String, List<String>>();
        names = new HashMap<String, String>();
    }

    @Override
    public List<String> getServicesForRole(final String role) {
        List<String> roleServices;

        if (services.containsKey(role)) {
            roleServices = services.get(role);
        } else {
            roleServices = new ArrayList<String>();
        }

        return roleServices;
    }

    @Override
    public String getServiceForRole(final String role) {
        String service;

        if (services.containsKey(role)) {
            service = services.get(role).get(0);
        } else {
            service = "";
        }

        return service;
    }

    @Override
    public String getServiceForName(final String name) {
        String sservice;

        if (names.containsKey(name)) {
            sservice = names.get(name);
        } else {
            sservice = "";
        }

        return sservice;
    }

    @Override
    public String getServiceByIndex(final String role, final int index) {
        String service;

        if (!services.containsKey(role) || services.get(role).isEmpty()) {
            service = "";
        } else {
            final List<String> roleServices = services.get(role);
            final int arrayIndex = index % roleServices.size();
            service = roleServices.get(arrayIndex);
        }

        return service;
    }

    @Override
    public String addService(final String role, final String name, final String endpoint) {
        if (!services.containsKey(role)) {
            services.put(role, new ArrayList<String>());
        }

        final List<String> roleServices = services.get(role);
        if (!roleServices.contains(endpoint)) {
            roleServices.add(endpoint);
            names.put(name, endpoint);
        }

        return "OK";
    }

    @Override
    public String removeService(final String role, final String name) {
        String answer;

        if (names.containsKey(name)) {
            removeNameAndRole(name, role);
            answer = "OK";
        } else {
            answer = "Name not found";
        }

        return answer;
    }

    /*
     * "names" must contain the key "name"
     */
    private void removeNameAndRole(final String name, final String role) {
        final String service = names.get(name);

        names.remove(name);
        removeFromRole(role, service);
    }

    /*
     * Ignores silently if there's no role or endpoint for the role
     */
    private void removeFromRole(final String role, final String endpoint) {
        if (services.containsKey(role)) {
            services.get(role).remove(endpoint);
        }
    }
}