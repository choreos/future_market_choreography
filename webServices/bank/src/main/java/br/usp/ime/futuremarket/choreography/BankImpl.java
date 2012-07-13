package br.usp.ime.futuremarket.choreography;

import javax.jws.WebService;

import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Bank")
public class BankImpl implements Bank {

    private static final String REL_PATH = "bank/bank";

    public BankImpl() {
        this(true);
    }

    public BankImpl(final boolean useRegistry) {
        if (useRegistry) {
            register();
        }
    }

    private void register() {
        final FutureMarket futureMarket = new FutureMarket();
        futureMarket.register(FutureMarket.BANK_ROLE, "Bank", REL_PATH);
    }

	@Override
	public String requestPayment(PurchaseInfo purchaseInfo,
			CustomerInfo customerInfo) {
		return "authorized";
	}
    
}