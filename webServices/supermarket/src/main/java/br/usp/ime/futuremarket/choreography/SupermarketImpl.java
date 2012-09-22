package br.usp.ime.futuremarket.choreography;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractFutureMarket;
import br.usp.ime.futuremarket.AbstractSupermarket;
import br.usp.ime.futuremarket.AbstractWSInfo;
import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.Supermarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/choreography/supermarket",
        endpointInterface = "br.usp.ime.futuremarket.Supermarket")
public class SupermarketImpl extends AbstractSupermarket {

    private Bank bank = null;

    public SupermarketImpl() throws IOException, InterruptedException {
        super();
    }

    @Override
    public Purchase purchase(final ShopList list, final CustomerInfo customer) throws IOException {
        final Purchase purchase = getFromStock(list, customer);

        final boolean isPaid = getBank().requestPayment(list.getPrice(), customer);
        purchase.setIsPaid(isPaid);

        final Shipper shipper = market.getClient(getShipperBaseAddress(), Shipper.class);
        shipper.deliver(purchase);

        return purchase;
    }

    @Override
    protected void buy() throws IOException {
        final Supermarket seller = market.getClient(getSellerBaseAddress(), Supermarket.class);
        seller.purchase(shopList, getCostumerInfo());
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
    public void reset() throws IOException, InterruptedException {
        super.reset();
        bank = null;
    }

    private Bank getBank() throws IOException {
        if (bank == null) {
            createBank();
        }
        return bank;
    }

    private void createBank() throws IOException {
        synchronized (this) {
            if (bank == null) {
                bank = market.getClientByRole(Role.BANK, Bank.class);
            }
        }
    }
}