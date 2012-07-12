package br.usp.ime.futuremarket;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Registry {

    @WebMethod
    public List<String> getList(String role);

    @WebMethod
    public String getFirst(String role);

    @WebMethod
    public String getByIndex(final String role, final int index);

    @WebMethod
    public String add(String role, String name, String endpoint);

    @WebMethod
    public String remove(String role, String name);

    @WebMethod
    public String getServiceEndpoint(String name);
}