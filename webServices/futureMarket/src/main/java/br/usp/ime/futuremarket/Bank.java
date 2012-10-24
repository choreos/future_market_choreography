package br.usp.ime.futuremarket;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface Bank {

    @WebMethod
    boolean requestPayment(double amount, CustomerInfo customer);
}