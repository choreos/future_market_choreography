package br.usp.ime.futuremarket;

import java.util.Calendar;

public class Delivery {
    private Calendar date;
    private String status;
    private Purchase purchase;

    public Delivery() {
        // Avoiding IllegalAnnotationExceptions
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(final Calendar date) {
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