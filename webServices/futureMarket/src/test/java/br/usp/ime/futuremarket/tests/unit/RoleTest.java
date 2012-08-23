package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.Role;

public class RoleTest {
    @Test
    public void testLowerCaseNames() {
        assertEquals("supermarket", Role.SUPERMARKET.toString());
    }

    @Test
    public void testRoleFromString() {
        assertEquals(Role.SUPERMARKET, Role.getByValue("supermarket"));
    }
}