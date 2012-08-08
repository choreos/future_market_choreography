package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractSupermarket;
import br.usp.ime.futuremarket.Purchase;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Supermarket")
public class SupermarketImpl extends AbstractSupermarket {

    private Orchestrator orchestrator = null;

    public SupermarketImpl() throws IOException {
        super();
    }

    @Override
    protected void buy() throws IOException {
        final Purchase purchase = getOrchestrator().smPurchase(shopList, getCostumerInfo(),
                sellerBaseAddr);
        getOrchestrator().getShipmentData(purchase);
    }

    private Orchestrator getOrchestrator() throws IOException {
        if (orchestrator == null) {
            final String role = Role.ORCHESTRATOR;
            final String service = ServiceName.ORCHESTRATOR;
            orchestrator = market.getClientRoundRobin(role, service, Orchestrator.class);
        }
        return orchestrator;
    }
}