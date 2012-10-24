package br.usp.ime.futuremarket.choreography;

import br.usp.ime.futuremarket.AbstractFutureMarket;
import br.usp.ime.futuremarket.AbstractWSInfo;

public class FutureMarket extends AbstractFutureMarket {

    public FutureMarket(final String registryWsdl) {
        super(registryWsdl);
    }

    @Override
    protected String baseAddressToWsdl(final String baseAddress) {
        return baseAddress + "choreography?wsdl";
    }

    @Override
    protected AbstractWSInfo getWSInfo() {
        return new WSInfo();
    }
}