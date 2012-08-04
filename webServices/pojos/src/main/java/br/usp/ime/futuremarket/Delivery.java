package br.usp.ime.futuremarket;

import java.util.Date;

public class Delivery {
    private Date date;
    private String status;
    private Purchase purchase;
    
    public Delivery() {
        // Avoiding IllegalAnnotationExceptions
    }
    
    public Delivery(final Date date, final String status, final Purchase purchase) {
        this.date = date;
        this.status = status;
        this.purchase = purchase;
    }

    public Date getDate() {
        return date;
    }
    
    public void setDate(final Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Purchase getPurchase() {
        return purchase;
    }
    
    public void setPurchase(final Purchase purchase) {
        this.purchase = purchase;
    }
}