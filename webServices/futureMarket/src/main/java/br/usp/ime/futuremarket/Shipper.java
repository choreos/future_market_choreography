package br.usp.ime.futuremarket;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface Shipper {

    @WebMethod
    boolean deliver(final Purchase purchase);

    @WebMethod
    Delivery getDelivery(final Purchase purchase);
    
    @WebMethod
    String setInvocationAddress(String registerWsdl) throws IOException;
}