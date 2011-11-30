package eu.choreos.runner;

import javax.xml.ws.Endpoint;

import eu.choreos.services.SM1;
import eu.choreos.services.SM2;
import eu.choreos.services.SM3;



public class SMRunner {

	public static void main(String[] args) {
		String prefix = "http://localhost:4321/WS/";
		
		Endpoint e = Endpoint.create(new SM1());
		e.publish(prefix + "SM1");
		
		e = Endpoint.create(new SM2());
		e.publish(prefix + "SM2");
		
		e = Endpoint.create(new SM3());
		e.publish(prefix + "SM2");
	}
}
