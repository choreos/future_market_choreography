package br.usp.ime.futuremarket;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HelloWorld {

    @WebMethod
    public String sayHello(final String name);
}
