package eu.choreos.services;

import eu.choreos.models.Product;

public class CheapestProduct extends Product{
	
	private String smEndpoint;
	
	
	public String getSmEndpoint() {
		return smEndpoint;
	}
	
	public void setSmEndpoint(String smEndpoint) {
		this.smEndpoint = smEndpoint;
	}
}
