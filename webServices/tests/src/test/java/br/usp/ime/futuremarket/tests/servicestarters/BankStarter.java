package br.usp.ime.futuremarket.tests.servicestarters;

import javax.xml.ws.Endpoint;

import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.BankImpl;

public class BankStarter {
	public static final String BANK = "bank";
	private static final int BANK_PORT = 12001;
	public static final String BANK_ADDRESS = "http://0.0.0.0:"+BANK_PORT+"/"+BANK+"/choreography";
	private static Endpoint endpoint;
	
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		Bank bank = new BankImpl();
		endpoint = Endpoint.create(bank);
		endpoint.publish(BANK_ADDRESS);
	}
}
