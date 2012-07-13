package br.usp.ime.futuremarket.choreography;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Bank {

    @WebMethod
    public String requestPayment(PurchaseInfo purchaseInfo, CustomerInfo customerInfo);

}