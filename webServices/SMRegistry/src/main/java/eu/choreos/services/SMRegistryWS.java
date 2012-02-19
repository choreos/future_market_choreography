package eu.choreos.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface SMRegistryWS {

    @WebMethod
    public List<String> getList(String role);

    @WebMethod
    public String getFirst(String role);

    @WebMethod
    public String add(String role, String endpoint);

    @WebMethod
    public String remove(String role, String endpoint);

}