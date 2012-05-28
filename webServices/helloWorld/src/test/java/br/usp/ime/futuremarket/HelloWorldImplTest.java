package br.usp.ime.futuremarket;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelloWorldImplTest {
    private final HelloWorld helloWorld = new HelloWorldImpl();
    
    private String getExpected(final String name) {
        return "Hello, " + name + "!";
    }

    @Test
    public void shouldSayHelloToWorld() {
        final String name = "World";
        assertEquals(getExpected(name), helloWorld.sayHello(name));
    }
    
    @Test
    public void shouldSayHelloToArthur() {
        final String name = "Arthur";
        assertEquals(getExpected(name), helloWorld.sayHello(name));
    }

}
