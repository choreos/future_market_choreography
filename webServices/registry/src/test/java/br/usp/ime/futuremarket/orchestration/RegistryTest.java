package br.usp.ime.futuremarket.orchestration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.orchestration.Registry;
import br.usp.ime.futuremarket.orchestration.RegistryImpl;

public class RegistryTest {
    private Registry registry;
    private static final String ROLE = "TestRole"; 
    
    @Test
    public void shouldReturnCorrectIndex() {
        registry = new RegistryImpl();
        registry.add(ROLE, "TestName1", "TestEndpoint1");
        registry.add(ROLE, "TestName2", "TestEndpoint2");

        assertEquals("TestEndpoint1", registry.getByIndex(ROLE, 0));
        assertEquals("TestEndpoint2", registry.getByIndex(ROLE, 1));
    }
    
    @Test
    public void shouldNotGetArrayOutOfBounds() {
        registry = new RegistryImpl();
        final String role = "TestRole";
        registry.add(role, "TestName1", "TestEndpoint1");
        registry.add(role, "TestName2", "TestEndpoint2");

        assertEquals("TestEndpoint1", registry.getByIndex(role, 2));
        assertEquals("TestEndpoint2", registry.getByIndex(role, 3));
    }
}