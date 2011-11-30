package eu.choreos.runner;

import javax.xml.ws.Endpoint;

import eu.choreos.services.CustomerWS;
import eu.choreos.services.SMRegistryWS;
import eu.choreos.services.ShipperWS;



public class ServiceRunner {

	public static void main(String[] args) {
		String prefix = "http://localhost:1234/";
		
		Endpoint e = Endpoint.create(new CustomerWS());
		e.publish(prefix + "customerWS");
		
		e = Endpoint.create(new SMRegistryWS());
		e.publish(prefix + "smregistry");
		
		e = Endpoint.create(new ShipperWS());
		e.publish(prefix + "shipperWS");
	}
}
