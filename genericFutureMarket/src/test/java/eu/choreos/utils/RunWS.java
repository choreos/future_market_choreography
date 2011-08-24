package eu.choreos.utils;

import java.util.HashMap;

import javax.xml.ws.Endpoint;

import eu.choreos.roles.SupermarketCustomerRole;
import eu.choreos.roles.SupermarketRole;
import eu.choreos.services.CarrefuturWS;
import eu.choreos.services.CustomerWS;
import eu.choreos.services.FutureMartWS;
import eu.choreos.services.PaoDoFuturoWS;
import eu.choreos.services.SMRegistryWS;

public class RunWS {

	private static HashMap<String, Endpoint>  services = new HashMap<String, Endpoint>();
	private static final String PREFIX = "http://localhost:1234/";

	
	public static void start(Object serviceInstance, String wsName){
		Endpoint endpoint = Endpoint.create(serviceInstance);
		endpoint.publish(PREFIX + wsName);
		
		services.put(wsName, endpoint);
	}
	
	public static void stop(String wsName){
		services.get(wsName).stop();
	}
	
	public static void restart(Object serviceInstance, String wsName){
		stop(wsName);
		start(serviceInstance, wsName);
	}
	
	public static void main(String[] args) {	
		RunWS.start(new SupermarketRole(), "supermarketRole");
		RunWS.start(new SupermarketCustomerRole(), "supermarketCustomerRole");
		RunWS.start(new FutureMartWS(), "futureMartWS");
		RunWS.start(new SMRegistryWS(), "smregistry");
		RunWS.start(new CarrefuturWS(), "carrefuturWS");
		RunWS.start(new PaoDoFuturoWS(), "paoDoFuturoWS");
		RunWS.start(new CustomerWS(), "customerWS");
	}

}
