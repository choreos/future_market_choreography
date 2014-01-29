package br.usp.ime.futuremarket.tests.servicestarters;

import javax.xml.ws.Endpoint;

import br.usp.ime.futuremarket.Registry;
import br.usp.ime.futuremarket.RegistryImpl;

public class RegistryStarter {
	
	public static final String REGISTRY = "registry";
	private static final int REGISTRY_PORT = 12000;
	public static final String REGISTRY_ADDRESS = "http://0.0.0.0:"+REGISTRY_PORT+"/" + REGISTRY + "/endpoint";
	private static Endpoint endpoint;
	
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		Registry registry = new RegistryImpl();
		endpoint = Endpoint.create(registry);
		endpoint.publish(REGISTRY_ADDRESS);
	}
}
