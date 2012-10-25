package br.usp.ime.futuremarket;

import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/bank",
        endpointInterface = "br.usp.ime.futuremarket.Bank")
public class BankImpl extends EnactmentEngineImpl implements Bank {

    public BankImpl() {
        super("bank");
    }

    @Override
    public boolean requestPayment(final double amount, final CustomerInfo customer) {
        return true;
    }
}