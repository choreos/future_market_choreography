package br.usp.ime.futuremarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistryUtils {

	public static Map<String, List<String>> toMap(List<String> services) {
		Map<String, List<String>> resultServices = new HashMap<String, List<String>>();
		
		String currentServiceName = null;
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).startsWith("sName:")) {
				// start position
				currentServiceName = services.get(i).split(":")[1];
				if (!resultServices.containsKey(currentServiceName))
					resultServices.put(currentServiceName, new ArrayList<String>());
			} else {				
				resultServices.get(currentServiceName).add(services.get(i));
			}
		}
		
		return resultServices;
	}
}
