package br.usp.ime.futuremarket.choreography;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractSupermarket;
import br.usp.ime.futuremarket.AbstractWSInfo;
import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.Supermarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/choreography/supermarket", endpointInterface = "br.usp.ime.futuremarket.Supermarket")
public class SupermarketImpl extends AbstractSupermarket {

	private String bankName = "";

	public SupermarketImpl() throws IOException, InterruptedException {
		super(new FutureMarket());
	}

	@Override
	public Purchase purchase(final ShopList list, final CustomerInfo customer) {
		Purchase purchase = null;
		try {
			purchase = getFromStock(list, customer);
		} catch (IOException e) {
		}

		boolean isPaid = false;
		try {
			isPaid = getBank().requestPayment(list.getPrice(),
					customer);
		} catch (IOException e) {
		}
		purchase.setIsPaid(isPaid);

		Shipper shipper = null;
		try {
			shipper = market.getDependency(getShipperName(),
					Shipper.class);
		} catch (IOException e) {
		}
		shipper.deliver(purchase);

		return purchase;
	}

	@Override
	protected void buy() throws IOException {
		final Supermarket seller = market.getDependency(getSellerName(),
				Supermarket.class);
		seller.purchase(shopList, getCostumerInfo());
	}

	@Override
	protected AbstractWSInfo getWSInfo() {
		return new WSInfo();
	}

	@Override
	public void reset() {
		super.reset();
		bankName = "";
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
				bankName = "bank"; // TODO: get available banks from market
			}
		}
	}
}