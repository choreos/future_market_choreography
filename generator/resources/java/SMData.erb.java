package eu.choreos.services;

import java.util.HashMap;

public class SM<%= @id %>Data {
	
	public static double getPrice(String name) {
		HashMap<String, Double> priceTable = new HashMap<String, Double>();
		
		<% @number_of_products.times do |i| %>
		priceTable.put("<%= "product#{i+1}"%>", <%= @price_generator.call(i+1) %>);
		<% end %>
		
		return priceTable.get(name);
	}

}
