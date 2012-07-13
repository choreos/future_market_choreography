package br.usp.ime.futuremarket.orchestration;

public class DeliveryInfo {

    private String id;

    private String date;

    private String status;

    private PurchaseInfo purchaseInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PurchaseInfo getPurchase() {
        return purchaseInfo;
    }

    public void setPurchase(PurchaseInfo purchaseInfo) {
        this.purchaseInfo = purchaseInfo;
    }
}
