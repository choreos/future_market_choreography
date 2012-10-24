package br.usp.ime.futuremarket.orchestration;

import br.usp.ime.futuremarket.AbstractFutureMarket;
import br.usp.ime.futuremarket.AbstractWSInfo;

public class FutureMarket extends AbstractFutureMarket {

    public FutureMarket(final String registryWsdl) {
        super(registryWsdl);
    }

    @Override
    protected String baseAddressToWsdl(final String baseAddress) {
        return baseAddress + "orchestration?wsdl";
    }

    @Override
    protected AbstractWSInfo getWSInfo() {
        return new WSInfo();
    }
}