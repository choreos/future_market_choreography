package br.usp.ime.interceptor;

import eu.choreos.vv.clientgenerator.WSClient;

public class Registry {

	private static final String REGISTRY_ENDPOINT = "http://localhost:8080/registry/endpoint?wsdl";

	public void removeService(String role, String name) throws Exception {
		WSClient client = new WSClient(REGISTRY_ENDPOINT);
		client.request("removeService", role, name);
	}

	public void addService(String role, String name, String endpoint) throws Exception {
		WSClient client = new WSClient(REGISTRY_ENDPOINT);
		client.request("addService", role, name, endpoint);
	}
}
