package br.usp.ime.futuremarket.orchestration;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.usp.ime.futuremarket.orchestration.CustomerInfo;
import br.usp.ime.futuremarket.orchestration.PurchaseInfo;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Bank {

    @WebMethod
    public String requestPayment(PurchaseInfo purchaseInfo, CustomerInfo customerInfo);

}