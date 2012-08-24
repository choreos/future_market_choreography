package br.usp.ime.futuremarket.tests.acceptance.choreography;

import java.io.IOException;

import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.Portal;
import br.usp.ime.futuremarket.tests.acceptance.AbstractAcceptanceTest;

/**
 * Acceptance test for use case 3
 * 
 * @author cadu
 * 
 */

public class AcceptanceTest extends AbstractAcceptanceTest {

    @Override
    public FutureMarket getFutureMarket() {
        return new FutureMarket();
    }

    @Override
    protected Portal getPortal() throws IOException {
        return market.getClientByRole(Role.PORTAL, Portal.class);
    }
}