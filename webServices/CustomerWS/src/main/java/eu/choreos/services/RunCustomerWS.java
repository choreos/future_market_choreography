package eu.choreos.services;

import java.util.Scanner;

import javax.xml.ws.Endpoint;

public class RunCustomerWS {
	private static Endpoint endpoint;

	static public void start() {
		endpoint = Endpoint.create(new CustomerWS());
		endpoint.publish("http://localhost:1234/customerWS");
	}
	
	static public void stop() {
		endpoint.stop();
	}
	
	public static void main(String[] args) {
		start();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Write 'ciao' and press <enter> to stop");
		scanner.next();
		stop();
		
	}
	
	
}
