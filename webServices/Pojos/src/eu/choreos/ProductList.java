package eu.choreos;

import java.util.List;

public class ProductList {
	
	private ProductPrice[] priceList;

	public ProductPrice[] getPriceList() {
		return priceList;
	}

	public void setPriceList(ProductPrice[] priceList) {
		this.priceList = priceList;
	}
	
	public void setPriceList(List<ProductPrice> priceList) {
		this.priceList = (ProductPrice[]) priceList.toArray();
	}

}
