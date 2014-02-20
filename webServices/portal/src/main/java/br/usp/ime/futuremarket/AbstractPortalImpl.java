package br.usp.ime.futuremarket;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import br.usp.ime.futuremarket.choreography.Portal;

public abstract class AbstractPortalImpl extends EnactmentEngineImpl implements
		Portal {

	public AbstractPortalImpl(final AbstractFutureMarket market)
			throws IOException {
		super(getServiceName(), market);
	}

	@Override
	public ShopList getLowestPrice(final ShopList list) {
		ShopList smList;
		try {
			for (Supermarket supermarket : getSupermarkets()) {
				smList = supermarket.getPrices(list);
				lookForCheaperProducts(list, smList);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Delivery getDelivery(final Purchase purchase) {
		final String shipperName = purchase.getShipper();
		Shipper shipper = null;
		try {
			shipper = market
					.getDependency(shipperName, Shipper.class);
		} catch (IOException e) {
			
		}
		return shipper.getDelivery(purchase);
	}

	@Override
	public Set<Purchase> purchase(final ShopList list,
			final CustomerInfo customer) {
		final Set<Purchase> purchases = new HashSet<Purchase>();
		final Collection<ShopList> listsPerSm = splitListBySm(list);

		Purchase purchase = null;
		for (ShopList smList : listsPerSm) {
			try {
				purchase = purchaseFromOneStore(smList, customer);
				purchases.add(purchase);
			} catch (IOException e) {
			}
		}

		return purchases;
	}

	protected Purchase purchaseFromOneStore(final ShopList list,
			final CustomerInfo customer) throws IOException {
		final String sellerName = list.getSeller();
		final Supermarket seller = market
				.getDependency(sellerName, Supermarket.class);
		return seller.purchase(list, customer);
	}

	private static String getServiceName() throws IOException {
		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		final InputStream propFile = loader
				.getResourceAsStream("portal.properties");
		final Properties properties = new Properties();
		properties.load(propFile);

		return properties.getProperty("name");
	}

	private Collection<ShopList> splitListBySm(final ShopList list) {
		// <SM baseAddr, ShopList>
		final Map<String, ShopList> result = new HashMap<String, ShopList>();

		ShopList smList;
		for (ShopListItem item : list.getShopListItems()) {
			smList = getSupermarketList(result, item.getSeller());
			smList.put(item);
		}
		return result.values();
	}

	private ShopList getSupermarketList(final Map<String, ShopList> lists,
			final String supermarket) {
		if (!lists.containsKey(supermarket)) {
			lists.put(supermarket, new ShopList());
		}
		return lists.get(supermarket);
	}

	private void lookForCheaperProducts(final ShopList list,
			final ShopList candidateList) {
		Product candidateProduct;
		ShopListItem item;

		for (ShopListItem candidateItem : candidateList.getShopListItems()) {
			candidateProduct = candidateItem.getProduct();
			item = list.getItem(candidateProduct);
			lookForCheaperProduct(item, candidateItem, candidateProduct);
		}
	}

	private void lookForCheaperProduct(final ShopListItem item,
			final ShopListItem candidateItem, final Product candidateProduct) {
		final Product product = item.getProduct();

		if (candidateProduct.getPrice() < product.getPrice()) {
			product.setPrice(candidateProduct.getPrice());
			item.setSeller(candidateItem.getSeller());
		}
	}

	private Collection<Supermarket> getSupermarkets() throws IOException {
		return market.getDependencyByRole(Role.SUPERMARKET, Supermarket.class);
	}
}