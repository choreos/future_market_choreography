package br.usp.ime.futuremarket;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Shipper {

    @WebMethod
    String deliver(final Purchase purchase);

    @WebMethod
    Delivery getDelivery(final Purchase purchase);
}