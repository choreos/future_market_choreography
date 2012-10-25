package br.usp.ime.futuremarket.choreography;

import java.io.IOException;
import java.util.List;

import javax.jws.WebService;

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

    private String bankBaseAddr = "";

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
    public void reset() throws IOException, InterruptedException {
        super.reset();
        bankBaseAddr = "";
    }

    private Bank getBank() throws IOException {
        if (bankBaseAddr.isEmpty()) {
            setBankBaseAddr();
        }
        return market.getClient(bankBaseAddr, Bank.class);
    }

    private void setBankBaseAddr() throws IOException {
        synchronized (bankBaseAddr) {
            if (bankBaseAddr.isEmpty()) {
                final List<String> banks = market.getBaseAddresses(Role.BANK);
                bankBaseAddr = banks.get(0);
            }
        }
    }
}