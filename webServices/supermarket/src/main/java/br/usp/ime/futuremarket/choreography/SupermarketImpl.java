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
    private Supermarket seller = null;
    private Shipper shipper = null;

    public SupermarketImpl() throws IOException {
        super();
    }

    @Override
    public Purchase purchase(final ShopList list, final CustomerInfo customer) throws IOException {
        final boolean isPaid = getBank().requestPayment(list.getPrice(), customer);
        final Purchase purchase = getFromStock(list, customer);
        purchase.setIsPaid(isPaid);
        getShipper().deliver(purchase);

        return purchase;
    }

    @Override
    protected void buy() throws IOException {
        getSeller().purchase(shopList, getCostumerInfo());
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
        bank = null;
        seller = null;
        shipper = null;
    }

    private Bank getBank() throws IOException {
        synchronized (this) {
            if (bank == null) {
                bank = market.getClientByRole(Role.BANK, Bank.class);
            }
        }
        return bank;
    }

    private Shipper getShipper() throws IOException {
        synchronized (this) {
            if (shipper == null) {
                shipper = market.getClient(getShipperBaseAddr(), Shipper.class);
            }
        }
        return shipper;
    }

    private Supermarket getSeller() throws IOException {
        synchronized (this) {
            if (seller == null) {
                seller = market.getClient(getSellerBaseAddr(), Supermarket.class);
            }
        }
        return seller;
    }
}