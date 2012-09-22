package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Stock;

public class StockTest {
    private static final String PRODNAME = "testMe";

    @Test
    public void shouldFindProductByName() {
        final Product prodA = new Product();
        prodA.setName(PRODNAME);

        final Product prodB = new Product();
        prodB.setName(PRODNAME);

        final Stock stock = new Stock();
        stock.add(prodA, 1);

        final Product stockProd = stock.search(prodB);
        assertNotNull(stockProd);
        assertEquals(prodA, stockProd);
    }
}