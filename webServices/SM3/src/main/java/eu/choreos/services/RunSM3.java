package eu.choreos.services;

import java.util.Scanner;

import javax.xml.ws.Endpoint;

public class RunSM3 {
	private static Endpoint endpoint;

	static public void start(String port) {
		endpoint = Endpoint.create(new SM3());
		endpoint.publish("http://localhost:" + port + "/WS/SM3");
	}
	
	static public void stop() {
		endpoint.stop();
	}
	
	public static void main(String[] args) {
		start(args[0]);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Write 'exit' and press <enter> to stop");
		scanner.next();
		stop();
		
	}
	
	
}
