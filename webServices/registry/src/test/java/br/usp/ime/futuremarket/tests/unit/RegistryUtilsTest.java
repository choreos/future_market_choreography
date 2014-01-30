package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.futuremarket.RegistryUtils;

public class RegistryUtilsTest {
	
	private List<String> service1 = new ArrayList<String>();
	private List<String> service2 = new ArrayList<String>();
	private List<String> service3 = new ArrayList<String>();
	private List<String> servicesOfRole = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		service1.add("sName:ser1");
		service1.add("http://location11");
		service1.add("http://location12");
		service1.add("http://location13");
		
		service2.add("sName:ser2");
		service2.add("http://location21");
		
		service3.add("sName:ser3");
		
		servicesOfRole.addAll(service1);
		servicesOfRole.addAll(service2);
		servicesOfRole.addAll(service3);
	}

	@After
	public void tearDown() throws Exception {
		service1 = new ArrayList<String>();
		service2 = new ArrayList<String>();
		service3 = new ArrayList<String>();
		servicesOfRole = new ArrayList<String>();
	}

	@Test
	public void test() {
		assertTrue(RegistryUtils.toMap(servicesOfRole).containsKey("ser1"));
		assertTrue(RegistryUtils.toMap(servicesOfRole).containsKey("ser2"));
		assertTrue(RegistryUtils.toMap(servicesOfRole).containsKey("ser3"));
		
		assertTrue(RegistryUtils.toMap(servicesOfRole).get("ser1").size() == 3);
		assertTrue(RegistryUtils.toMap(servicesOfRole).get("ser1").contains("http://location11"));
		assertTrue(RegistryUtils.toMap(servicesOfRole).get("ser2").size() == 1);
		assertTrue(RegistryUtils.toMap(servicesOfRole).get("ser2").contains("http://location21"));
		assertFalse(RegistryUtils.toMap(servicesOfRole).get("ser2").contains("http://location11"));
		assertTrue(RegistryUtils.toMap(servicesOfRole).get("ser3").isEmpty());
		
	}
}
