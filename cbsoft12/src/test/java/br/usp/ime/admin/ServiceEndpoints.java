package br.usp.ime.admin;

public enum ServiceEndpoints {
	SM1("http://localhost:1221/sm1"), 
	CORREIOS("http://localhost:9000/correiosMock"), 
	DELIVER_EVW("http://localhost:1234/deliverEVW");
	
	private String endpoint;
	
	ServiceEndpoints(String endpoint){
		this.endpoint = endpoint;
	}
	
	public String getEndpoint(){
		return endpoint;
	}
}
