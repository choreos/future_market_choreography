package br.usp.ime.futuremarket.choreography;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractSupermarket;
import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ServiceName;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.Supermarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
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

    private Bank getBank() throws IOException {
        if (bank == null) {
            bank = market.getClientByRole(Role.BANK, ServiceName.BANK, Bank.class);
        }
        return bank;
    }

    private Shipper getShipper() throws IOException {
        if (shipper == null) {
            final String baseAddress = market.getBaseAddress(shipperName);
            shipper = market.getClient(baseAddress, ServiceName.SHIPPER, Shipper.class);
        }
        return shipper;
    }

    private Supermarket getSeller() throws MalformedURLException {
        if (seller == null) {
            seller = market.getClient(sellerBaseAddr, ServiceName.SUPERMARKET, Supermarket.class);
        }
        return seller;
    }
}