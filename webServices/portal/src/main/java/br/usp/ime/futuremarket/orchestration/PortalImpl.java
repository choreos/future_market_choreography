package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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

    private String bankName = "";

    public PortalImpl() throws IOException {
        super(new FutureMarket());
    }

    @Override
    public boolean deliver(final Purchase purchase) {
        final String shipperName = purchase.getShipper();
        Shipper shipper = null;
		try {
			shipper = market.getDependency(shipperName, Shipper.class);
		} catch (IOException e) {
		}
        return shipper.deliver(purchase);
    }

    @Override
    public Purchase smPurchase(final ShopList list, final CustomerInfo customer){
        Purchase purchaseFromOneStore = null;
		try {
			purchaseFromOneStore = super.purchaseFromOneStore(list, customer);
		} catch (IOException e) {
		}
		return purchaseFromOneStore;
    }

    @Override
    public boolean requestPayment(final double amount, final CustomerInfo customer) {
        boolean requestPayment = false;
		try {
			requestPayment = getBank().requestPayment(amount, customer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestPayment;
    }

    private Bank getBank() throws IOException {
        if (bankName.isEmpty()) {
            setBankName();
        }
        return market.getDependency(bankName, Bank.class);
    }

    private void setBankName() throws IOException {
        synchronized (bankName) {
            if (bankName.isEmpty()) {
                bankName = "bank"; // TODO: get available bank from market
            }
        }
    }
}