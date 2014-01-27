package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Registry;
import br.usp.ime.futuremarket.RegistryImpl;

public class RegistryTest {
    private Registry registry;
    private static List<String> endpoints;
    private static final String ROLE = "TestRole";
    private static final String NAME = "foo";

    @BeforeClass
    public static void initializeLists() {
        endpoints = new ArrayList<String>();
    }

    @Before
    public void reset() {
        registry = new RegistryImpl();
        endpoints.clear();
    }

    private void setServiceEndpoints(final String role, final String name, final String... endpoint) {
    	endpoints = Arrays.asList(endpoint);
        registry.setInvocationAddress(role, name, Arrays.asList(endpoint));
    }

    @Test
    public void testUpdateByName() {
        setServiceEndpoints(ROLE, NAME, "oldValue1", "oldValue2");
        setServiceEndpoints(ROLE, NAME, "newValue1", "newValue2");

        assertEquals(endpoints.get(1), registry.getInstances(NAME).get(1));
    }
}