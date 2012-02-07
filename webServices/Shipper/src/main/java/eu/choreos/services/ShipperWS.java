package eu.choreos.services;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class ShipperWS {
	
	HashMap<Integer, String> deliveries = new HashMap<Integer, String>();
	
	@WebMethod
	public String setDelivery(String id, String zipcode){
		deliveries.put(Integer.parseInt(id), zipcode);
		return "ok";
	}
	
	@WebMethod
	public String getDateAndTime(String id){
		String zipCode = deliveries.get(Integer.parseInt(id));
		
		Calendar c = null;
		
		if(Integer.parseInt(zipCode.substring(0, 3)) <= 300)
			c = setTime(8, 12, 32);
		else if (Integer.parseInt(zipCode.substring(0, 3)) <= 600)
			c = setTime(15, 30, 42);
		else if (Integer.parseInt(zipCode.substring(0, 3)) <= 750)
			c = setTime(20, 15, 00);
		else 
			c = setTime(22, 30, 00);
		
		return c.getTime().toString();
	}
	
	private Calendar setTime(int hour, int minute, int second){
		Calendar c1 = new GregorianCalendar(2011, Calendar.DECEMBER, 24);
		c1.set(2011, Calendar.DECEMBER, 24, hour, minute, second);
		
		return c1;
	}
	
}
