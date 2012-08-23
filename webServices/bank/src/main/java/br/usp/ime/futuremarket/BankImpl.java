package br.usp.ime.futuremarket;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.choreography.FutureMarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/bank",
        endpointInterface = "br.usp.ime.futuremarket.Bank")
public class BankImpl implements Bank {
    public BankImpl() throws IOException {
        this(true);
    }

    public BankImpl(final boolean useRegistry) throws IOException {
        if (useRegistry) {
            register();
        }
    }

    private void register() throws IOException {
        final FutureMarket futureMarket = new FutureMarket();
        futureMarket.register(Role.BANK.toString());
    }

    @Override
    public boolean requestPayment(final double amount, final CustomerInfo customer) {
        return true;
    }
}