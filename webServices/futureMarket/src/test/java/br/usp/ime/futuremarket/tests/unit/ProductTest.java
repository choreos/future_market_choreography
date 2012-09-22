package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.futuremarket.Product;

public class ProductTest {
    private Product prodA, prodB;
    private static final String PRODNAME = "testMe";

    @Before
    public void initProducts() {

        prodA = new Product();
        prodA.setName(PRODNAME);

        prodB = new Product();
        prodB.setName(PRODNAME);
    }

    @Test
    public void testEqualityByName() {
        assertEquals(prodA, prodB);
    }

    @Test
    public void testSearchByKey() {
        final Map<Product, Integer> map = new HashMap<Product, Integer>();
        final Integer value = Integer.valueOf(42);
        map.put(prodA, value);

        assertEquals(value, map.get(prodB));
    }
}