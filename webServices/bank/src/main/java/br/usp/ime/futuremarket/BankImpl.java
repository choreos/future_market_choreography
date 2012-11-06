package br.usp.ime.futuremarket;

import javax.jws.WebService;

import br.usp.ime.futuremarket.choreography.FutureMarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/bank",
        endpointInterface = "br.usp.ime.futuremarket.Bank")
public class BankImpl extends EnactmentEngineImpl implements Bank {

    // TODO Remove architecture definition from FutureMarket import
    public BankImpl() {
        super("bank", new FutureMarket());
    }

    @Override
    public boolean requestPayment(final double amount, final CustomerInfo customer) {
        return true;
    }
}