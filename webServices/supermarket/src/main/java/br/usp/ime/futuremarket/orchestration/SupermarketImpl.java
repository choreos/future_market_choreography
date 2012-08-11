package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractSupermarket;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ShopList;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Supermarket")
public class SupermarketImpl extends AbstractSupermarket {

    private Orchestrator orchestrator = null;

    public SupermarketImpl() throws IOException {
        super();
    }

    @Override
    public Purchase purchase(final ShopList list, final CustomerInfo customer) throws IOException {
        final Orchestrator orch = getOrchestrator();

        final boolean isPaid = orch.requestPayment(list.getPrice(), customer);
        final Purchase purchase = getFromStock(list, customer);
        purchase.setIsPaid(isPaid);
        orch.deliver(purchase);

        return purchase;
    }

    @Override
    protected void buy() throws IOException {
        final Purchase purchase = getOrchestrator().smPurchase(shopList, getCostumerInfo());
        getOrchestrator().getDelivery(purchase);
    }

    private Orchestrator getOrchestrator() throws IOException {
        if (orchestrator == null) {
            orchestrator = market.getClientRoundRobin(Role.ORCHESTRATOR, ServiceName.ORCHESTRATOR,
                    Orchestrator.class);
        }
        return orchestrator;
    }

}