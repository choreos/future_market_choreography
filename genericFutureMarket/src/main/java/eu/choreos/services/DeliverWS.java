package eu.choreos.services;

import javax.jws.WebService;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

@WebService
public class DeliverWS {
	
	public static void main(String[] args) throws Exception {
		WSClient deliver = new WSClient("http://localhost:8084/petals/services/customerAlpha?wsdl");
		
		
		Item request = new ItemImpl("setDeliveryData");
		Item orderID = new ItemImpl("id");
		orderID.setContent("1");
		request.addChild(orderID);
		
		Item deliveryData = new ItemImpl("delivery");
		Item time = new ItemImpl("time");
		time.setContent("12:00");
		deliveryData.addChild(time);
		Item date = new ItemImpl("date");
		date.setContent("12-12-2012");
		deliveryData.addChild(date);
		request.addChild(deliveryData);
		
		deliver.request("setDeliveryData", request);
		
	}
	

}
