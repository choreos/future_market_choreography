package br.usp.ime.futuremarket.orchestration;

import br.usp.ime.futuremarket.Registry;
import br.usp.ime.futuremarket.Supermarket;

public class BaseTest {

    protected void resetAll() {
        reset(FutureMarket.SUPERMARKET_ROLE);
        reset(FutureMarket.SUPPLIER_ROLE);
        reset(FutureMarket.MANUFACTURER_ROLE);
    }

    // TODO: Resettable interface
    private void reset(String role) {
        final Registry registry = FutureMarket.getRegistry();
        
        for (String endpoint : registry.getList(role)) {
            Supermarket supermarket = FutureMarket.getClient(Supermarket.class, endpoint,
                    FutureMarket.SUPERMARKET_SERVICE);
            supermarket.reset();
        }
    }
}