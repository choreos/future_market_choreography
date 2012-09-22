package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractPortalImpl;
import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShopList;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/orchestration/portal",
        endpointInterface = "br.usp.ime.futuremarket.orchestration.Portal")
public class PortalImpl extends AbstractPortalImpl implements Portal {
    private Bank bank = null;

    public PortalImpl() throws IOException {
        super(new FutureMarket());
    }

    @Override
    public boolean deliver(final Purchase purchase) throws MalformedURLException {
        final String baseAddr = purchase.getShipper();
        final Shipper shipper = market.getClient(baseAddr, Shipper.class);
        return shipper.deliver(purchase);
    }

    @Override
    public Purchase smPurchase(final ShopList list, final CustomerInfo customer) throws IOException {
        return super.purchaseFromOneStore(list, customer);
    }

    @Override
    public boolean requestPayment(final double amount, final CustomerInfo customer)
            throws IOException {
        return getBank().requestPayment(amount, customer);
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