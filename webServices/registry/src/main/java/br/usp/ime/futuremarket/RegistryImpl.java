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
        final int index = getRoleIndex(role);

        increaseRoleIndex(role, index, roleServices.size());

        return roleServices.get(index);
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
            if (!services.containsKey(role)) {
                services.put(role, new ArrayList<String>());
            }

            final List<String> roleServices = services.get(role);
            if (!roleServices.contains(endpoint)) {
                roleServices.add(endpoint);
                names.put(name, endpoint);
            }
        }

        return "OK";
    }

    @Override
    public String removeService(final String role, final String name) {
        String answer;

        synchronized (this) {
            if (names.containsKey(name)) {
                removeNameAndRole(name, role);
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