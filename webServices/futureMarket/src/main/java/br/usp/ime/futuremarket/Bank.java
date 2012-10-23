package br.usp.ime.futuremarket;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface Bank {

    @WebMethod
    boolean requestPayment(double amount, CustomerInfo customer);
    
    @WebMethod
    String setInvocationAddress(String registerWsdl) throws IOException;
}