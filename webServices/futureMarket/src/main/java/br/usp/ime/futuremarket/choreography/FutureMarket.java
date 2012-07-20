package br.usp.ime.futuremarket.choreography;

import br.usp.ime.futuremarket.AbstractFutureMarket;

public class FutureMarket extends AbstractFutureMarket {
    @Override
    protected String baseAddressToWsdl(final String baseAddress) {
        return baseAddress + "/choreography?wsdl";
    }
}