package eu.choreos.services;

import java.util.Scanner;

import javax.xml.ws.Endpoint;

public class RunSM1 {
    private static Endpoint endpoint;

    static public void start() throws Exception {
	endpoint = Endpoint.create(new SM1());
	endpoint.publish("http://localhost:4321/WS/SM1");
    }

    static public void stop() {
	endpoint.stop();
    }

    public static void main(String[] args) throws Exception {
	start();
	Scanner scanner = new Scanner(System.in);
	System.out.println("Write 'ciao' and press <enter> to stop");
	scanner.next();
	stop();

    }

}