package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		initializeRole(role);
		mServices.get(role).put(service, endpoints);
		return "OK";
	}

	@Override
	public List<String> getServices(final String role) {
		if (mServices.containsKey(role)) {
			ArrayList<String> result = new ArrayList<String>();
			
			for (Entry<String, List<String>> service : mServices.get(role).entrySet()) {
				String serviceName = service.getKey();
				List<String> instances = service.getValue();
				
				ArrayList<String> serviceRepr = new ArrayList<String>();
				
				serviceRepr.add("sName:" +serviceName);
				serviceRepr.addAll(instances);
				
				result.addAll(serviceRepr);
			}
			
			return result;
		} else {
			return new ArrayList<String>();
		}
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