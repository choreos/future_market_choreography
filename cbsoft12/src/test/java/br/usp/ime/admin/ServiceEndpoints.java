package br.usp.ime.admin;

public enum ServiceEndpoints {
	SM1("http://localhost:1221/sm1");
	
	private String endpoint;
	
	ServiceEndpoints(String endpoint){
		this.endpoint = endpoint;
	}
	
	public String getEndpoint(){
		return endpoint;
	}
}
