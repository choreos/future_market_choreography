package br.usp.ime.futuremarket;

public class CustomerInfo {
    private String name;
    private String creditCard;
    private String address;

    public CustomerInfo() {
        // Avoiding IllegalAnnotationExceptions
    }

    public CustomerInfo(final String name, final String creditCard, final String address) {
        this.name = name;
        this.creditCard = creditCard;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(final String creditCard) {
        this.creditCard = creditCard;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
}