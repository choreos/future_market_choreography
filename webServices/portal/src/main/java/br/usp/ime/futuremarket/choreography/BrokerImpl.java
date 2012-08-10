package br.usp.ime.futuremarket.choreography;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.Supermarket;

public class BrokerImpl implements Broker {

    private final FutureMarket market = new FutureMarket();

    @Override
    public ShopList getLowestPrice(final ShopList list) throws IOException {
        ShopList smList;

        for (Supermarket supermarket : getSupermarkets()) {
            smList = supermarket.getPrices(list);
            lookForCheaperProducts(list, smList);
        }
        return list;
    }

    private void lookForCheaperProducts(final ShopList list, final ShopList candidateList) {
        Product candidateProduct;
        ShopListItem item;

        for (ShopListItem candidateItem : candidateList.getItems()) {
            candidateProduct = candidateItem.getProduct();
            item = list.getItem(candidateProduct);

            lookForCheaperProduct(item, candidateItem, candidateProduct);
        }
    }

    private void lookForCheaperProduct(final ShopListItem item, final ShopListItem candidateItem,
            final Product candidateProduct) {
        final Product product = item.getProduct();

        if (candidateProduct.getPrice() < product.getPrice()) {
            product.setPrice(candidateProduct.getPrice());
            item.setSellerEndpoint(candidateItem.getSellerEndpoint());
        }
    }

    private Collection<Supermarket> getSupermarkets() throws IOException {
        return market.getClients(Role.SUPERMARKET, ServiceName.SUPERMARKET, Supermarket.class);
    }

    @Override
    public Delivery getDelivery(final Purchase purchase) throws MalformedURLException {
        final String baseAddress = purchase.getShipper();
        final Shipper shipper = market.getClient(baseAddress, ServiceName.SHIPPER, Shipper.class);
        return shipper.getDelivery(purchase);
    }

    @Override
    public Set<Purchase> purchase(final ShopList list, final CustomerInfo customer)
            throws IOException {
        final Set<Purchase> purchases = new HashSet<Purchase>();
        // <SM baseAddress, SM ShopList>
        final Map<String, ShopList> listsPerSm = splitListBySm(list);

        ShopList smShopList;
        Purchase purchase;
        for (String smBaseAddr : listsPerSm.keySet()) {
            smShopList = listsPerSm.get(smBaseAddr);
            purchase = purchase(smBaseAddr, smShopList, customer);
            purchases.add(purchase);
        }

        return purchases;
    }

    private Purchase purchase(final String baseAddr, final ShopList list,
            final CustomerInfo customer) throws IOException {
        final Supermarket supermarket = market.getClient(baseAddr, ServiceName.SUPERMARKET,
                Supermarket.class);
        return supermarket.purchase(list, customer);
    }

    private Map<String, ShopList> splitListBySm(final ShopList list) {
        final Map<String, ShopList> result = new HashMap<String, ShopList>();

        ShopList smList;
        for (ShopListItem item : list.getItems()) {
            smList = getSupermarketList(result, item.getSellerEndpoint());
            smList.put(item);
        }
        return result;
    }

    private ShopList getSupermarketList(final Map<String, ShopList> lists, final String supermarket) {
        if (!lists.containsKey(supermarket)) {
            lists.put(supermarket, new ShopList());
        }
        return lists.get(supermarket);
    }
}