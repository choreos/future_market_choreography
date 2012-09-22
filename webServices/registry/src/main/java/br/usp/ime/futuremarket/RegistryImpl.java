package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/registry",
        endpointInterface = "br.usp.ime.futuremarket.Registry")
public class RegistryImpl implements Registry {

    // HashMap<role, ArrayList<endpoint>>
    private final Map<String, List<String>> services;
    // HashMap<name, endpoint>
    private final Map<String, String> names;
    private final Map<String, Integer> index;

    public RegistryImpl() {
        services = new HashMap<String, List<String>>();
        names = new HashMap<String, String>();
        index = new HashMap<String, Integer>();
    }

    @Override
    public List<String> getServices(final String role) {
        List<String> roleServices;

        synchronized (this) {
            if (services.containsKey(role)) {
                roleServices = services.get(role);
            } else {
                roleServices = new ArrayList<String>();
            }
        }

        return roleServices;
    }

    @Override
    public String getServiceByRole(final String role) {
        String service;

        synchronized (this) {
            if (services.containsKey(role)) {
                service = services.get(role).get(0);
            } else {
                service = "";
            }
        }

        return service;
    }

    @Override
    public String getServiceByName(final String name) {
        final String service = names.get(name);
        return (service == null) ? "" : service;
    }

    @Override
    public String getServiceRoundRobin(final String role) {
        String service;

        synchronized (this) {
            if (!services.containsKey(role) || services.get(role).isEmpty()) {
                service = "";
            } else {
                service = getNextService(role);
            }
        }

        return service;
    }

    private String getNextService(final String role) {
        final List<String> roleServices = services.get(role);
        int index = getRoleIndex(role);
        
        index = checkListBound(index, roleServices);

        increaseRoleIndex(role, index, roleServices.size());

        return roleServices.get(index);
    }
    
    private int checkListBound(int index, List list) {
    	return (list.size() > index) ? index : 0;
    }

    private void increaseRoleIndex(final String role, final int value, final int size) {
        final int safeValue = (value + 1) % size;
        index.put(role, safeValue);
    }

    private int getRoleIndex(final String role) {
        final Integer roleIndex = index.get(role);
        return (roleIndex == null) ? 0 : roleIndex;
    }

    @Override
    public String addService(final String role, final String name, final String endpoint) {
        synchronized (this) {
            // Avoid service without name if name is overridden
            removeFromRoleByName(role, name);
            initializeRole(role);

            final List<String> roleServices = services.get(role);
            if (!roleServices.contains(endpoint)) {
                roleServices.add(endpoint);
                names.put(name, endpoint);
            }
        }

        return "OK";
    }

    private void initializeRole(final String role) {
        if (!services.containsKey(role)) {
            services.put(role, new ArrayList<String>());
        }
    }

    private void removeFromRoleByName(final String role, final String name) {
        if (names.containsKey(name) && services.containsKey(role)) {
            final String service = names.get(name);
            services.get(role).remove(service);
        }
    }

    @Override
    public String removeService(final String role, final String name) {
        String answer;

        synchronized (this) {
            if (names.containsKey(name)) {
                removeNameAndEndpoint(name, role);
                answer = "OK";
            } else {
                answer = "Name not found";
            }
        }

        return answer;
    }

    /*
     * "names" must contain the key "name"
     */
    private void removeNameAndEndpoint(final String name, final String role) {
        final String endpoint = names.get(name);

        names.remove(name);
        removeEndpointFromRole(role, endpoint);
    }

    /*
     * Ignores silently if there's no role or endpoint for the role
     */
    private void removeEndpointFromRole(final String role, final String endpoint) {
        if (services.containsKey(role)) {
            services.get(role).remove(endpoint);
            removeRoleIfEmpty(role);
        }
    }

    private void removeRoleIfEmpty(final String role) {
        if (services.get(role).isEmpty()) {
            services.remove(role);
            index.remove(role);
        }
    }
}