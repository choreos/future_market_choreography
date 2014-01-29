package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/registry", endpointInterface = "br.usp.ime.futuremarket.Registry")
public class RegistryImpl implements Registry {

	// HashMap<role, Map<name,ArrayList<instances endpoint>>>
	private final Map<String, HashMap<String, List<String>>> mServices;

	public RegistryImpl() {
		mServices = new HashMap<String, HashMap<String, List<String>>>();
	}

	@Override
	public String setInvocationAddress(String role, String service,
			List<String> endpoints) {
		System.out.println("setInvocationAddress " + "role: " + role + " service: " +service + " endpo"
				+ "ints: " + endpoints);
		initializeRole(role);
		mServices.get(role).put(service, endpoints);
		System.out.println(mServices);
		return "OK";
	}

	@Override
	public HashMap<String, List<String>> getServices(final String role) {
		HashMap<String, List<String>> roleServices;

		synchronized (this) {
			if (mServices.containsKey(role)) {
				roleServices = mServices.get(role);
			} else {
				roleServices = new HashMap<String, List<String>>();
			}
		}

		return roleServices;
	}

	@Override
	public List<String> getInstances(final String service) {
		for (Map<String, List<String>> roleServices : mServices.values()) {
			for (String serviceKey : roleServices.keySet())
				if (serviceKey.equals(service))
					return roleServices.get(serviceKey);
		}
		return new ArrayList<String>();
	}

	private void initializeRole(final String role) {
		if (!mServices.containsKey(role)) {
			mServices.put(role, new HashMap<String, List<String>>());
		}
	}

	@Override
	public String removeInstance(String endpoint) {
		String answer = null;

		synchronized (this) {
			for (Map<String, List<String>> services : mServices.values()) {
				for (String service : services.keySet()) {
					List<String> instances = services.get(service);
					for (String instance : instances) {
						if (instance.equals(endpoint)) {
							instances.remove(instance);
							answer = "OK";
						}
					}
				}
			}
		}
		if (answer == null)
			answer = "Name not found";
		return answer;
	}
}