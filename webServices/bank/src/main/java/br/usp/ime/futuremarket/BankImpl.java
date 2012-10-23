package br.usp.ime.futuremarket;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.futuremarket.choreography.FutureMarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/bank",
        endpointInterface = "br.usp.ime.futuremarket.Bank")
public class BankImpl implements Bank {

    @Override
    public boolean requestPayment(final double amount, final CustomerInfo customer) {
        return true;
    }

	@Override
	public String setInvocationAddress(String registerWsdl) throws IOException {
	    final FutureMarket futureMarket = new FutureMarket();
        futureMarket.register(Role.BANK.toString(),registerWsdl);
		return "OK";
	}
}