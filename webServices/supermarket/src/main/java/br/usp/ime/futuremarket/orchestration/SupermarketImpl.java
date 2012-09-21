package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private final List<Portal> orchestrators;
    private Integer orchIndex;

    public SupermarketImpl() throws IOException {
        super();
        orchestrators = new ArrayList<Portal>();
        orchIndex = 0;
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
        synchronized (orchestrators) {
            orchestrators.clear();
        }
    }

    private Portal getOrchestrator() throws IOException {
        setOrchestratorsFromRegistry();
        final int index = getNextOrchestratorIndex();
        return orchestrators.get(index);
    }

    private void setOrchestratorsFromRegistry() throws IOException {
        synchronized (orchestrators) {
            if (orchestrators.isEmpty()) {
                orchestrators.addAll(market.getClients(Role.PORTAL, Portal.class));
            }
        }
    }

    private int getNextOrchestratorIndex() {
        int index;

        synchronized (orchIndex) {
            index = orchIndex;
            if (orchIndex == orchestrators.size() - 1) {
                orchIndex = 0;
            } else {
                orchIndex++;
            }
        }

        return index;
    }
}