package br.usp.ime.futuremarket.choreography;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractSupermarket;
import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ServiceName;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.Supermarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Supermarket")
public class SupermarketImpl extends AbstractSupermarket {
    
    private Bank bank = null;

    public SupermarketImpl() throws IOException {
        super();
    }

    @Override
    public Purchase purchase(final ShopList list, final CustomerInfo customer) throws IOException {
        final Purchase purchase = super.purchase(list, customer);
        requestPayment(purchase, customer);
        return purchase;
    }

    private void requestPayment(final Purchase purchase, final CustomerInfo customer)
            throws IOException {
        final String answer = getBank().requestPayment(purchase, customer);
        final boolean authorized = "authorized".equals(answer);
        purchase.setIsPaid(authorized);
    }

    @Override
    protected void buy() throws IOException {
        final Supermarket seller = market.getClient(sellerBaseAddr, ServiceName.SUPERMARKET,
                Supermarket.class);

        seller.purchase(shopList, getCostumerInfo());
    }

    private Bank getBank() throws IOException {
        if (bank == null) {
            final String role = Role.BANK;
            final String name = ServiceName.BANK;
            bank = market.getClientByRole(role, name, Bank.class);
        }
        return bank;
    }
}