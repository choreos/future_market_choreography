package br.usp.ime.futuremarket;

public class Purchase {
    private long number;
    private String seller;
    private String shipper;
    private ShopList shopList;
    private CustomerInfo customerInfo;
    private boolean isPaid = false;

    public Purchase() {
        // Avoiding IllegalAnnotationExceptions
    }

    public Purchase(final long number, final String seller, final String shipper,
            final ShopList list, final CustomerInfo customer) {
        this.number = number;
        this.seller = seller;
        this.shipper = shipper;
        this.shopList = list;
        this.customerInfo = customer;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(final long number) {
        this.number = number;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(final String sellerEndpoint) {
        this.seller = sellerEndpoint;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(final String shipperEndpoint) {
        this.shipper = shipperEndpoint;
    }

    public ShopList getShopList() {
        return shopList;
    }

    public void setShopList(final ShopList shopList) {
        this.shopList = shopList;
    }

    public double getValue() {
        return shopList.getPrice();
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(final CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(final boolean isPaid) {
        this.isPaid = isPaid;
    }
}