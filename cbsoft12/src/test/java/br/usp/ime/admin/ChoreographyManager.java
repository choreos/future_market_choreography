package br.usp.ime.admin;

import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import br.usp.ime.service.SM1WS;

public class ChoreographyManager {

	private Map<ServiceEndpoints, Endpoint> services;

	public ChoreographyManager() {
		services = new HashMap<ServiceEndpoints, Endpoint>();
		addServices();
	}

	private void addServices() {
		services.put(ServiceEndpoints.SM1, Endpoint.create(new SM1WS()));
	}

	public void start(ServiceEndpoints service) {
		Endpoint endpoint = services.get(service);
		endpoint.publish(service.getEndpoint());
	}

	public void stop(ServiceEndpoints service) {
		Endpoint endpoint = services.get(service);
		endpoint.stop();
	}

}
