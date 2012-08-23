package br.usp.ime.futuremarket.orchestration.tests.acceptance;

import java.io.IOException;

import br.usp.ime.futuremarket.AbstractFutureMarket;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.orchestration.FutureMarket;
import br.usp.ime.futuremarket.orchestration.Portal;
import br.usp.ime.futuremarket.tests.acceptance.AbstractAcceptanceTest;

/**
 * Acceptance test for use case 3
 * 
 * @author cadu
 * 
 */

public class AcceptanceTest extends AbstractAcceptanceTest {

    @Override
    public AbstractFutureMarket getFutureMarket() {
        return new FutureMarket();
    }

    @Override
    protected br.usp.ime.futuremarket.choreography.Portal getPortal() throws IOException {
        return market.getClientByRole(Role.PORTAL, Portal.class);
    }
}