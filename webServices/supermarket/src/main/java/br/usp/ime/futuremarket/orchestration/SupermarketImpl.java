package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractFutureMarket;
import br.usp.ime.futuremarket.AbstractSupermarket;
import br.usp.ime.futuremarket.AbstractWSInfo;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.ShopList;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/orchestration/supermarket",
        endpointInterface = "br.usp.ime.futuremarket.Supermarket")
public class SupermarketImpl extends AbstractSupermarket {

    private Portal orchestrator = null;

    public SupermarketImpl() throws IOException {
        super();
    }

    @Override
    public Purchase purchase(final ShopList list, final CustomerInfo customer) throws IOException {
        final Portal orch = getOrchestrator();
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

    @Override
    protected AbstractWSInfo getWSInfo() {
        return new WSInfo();
    }

    @Override
    protected AbstractFutureMarket getFutureMarket() {
        return new FutureMarket();
    }

    @Override
    public void reset() {
        super.reset();
        synchronized (this) {
            orchestrator = null;
        }
    }

    private Portal getOrchestrator() throws IOException {
        synchronized (this) {
            if (orchestrator == null) {
                orchestrator = market.getClientRoundRobin(Role.PORTAL, Portal.class);
            }
        }
        return orchestrator;
    }
}