package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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

    @BeforeClass
    public static void initializeLists() {
        endpoints = new ArrayList<String>();
    }

    @Before
    public void reset() {
        registry = new RegistryImpl();
        endpoints.clear();
    }

    @Test
    public void shouldRoundRobin() {
        addEndpoint(ROLE, "TestName1", "TestEndpoint1");
        addEndpoint(ROLE, "TestName2", "TestEndpoint2");

        assertEquals(endpoints.get(0), registry.getServiceRoundRobin(ROLE));
        assertEquals(endpoints.get(1), registry.getServiceRoundRobin(ROLE));
    }

    private void addEndpoint(final String role, final String name, final String endpoint) {
        endpoints.add(endpoint);
        registry.addService(role, name, endpoint);
    }

    @Test
    public void shouldCicleServicesOnRoundRobin() {
        addEndpoint(ROLE, "Foo1", "Bar1");
        addEndpoint(ROLE, "Foo2", "Bar2");

        assertEquals(endpoints.get(0), registry.getServiceRoundRobin(ROLE));
        assertEquals(endpoints.get(1), registry.getServiceRoundRobin(ROLE));
        assertEquals(endpoints.get(0), registry.getServiceRoundRobin(ROLE));
    }
}