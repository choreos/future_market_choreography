package br.usp.ime.futuremarket.functional;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.HelloWorld;
import br.usp.ime.futuremarket.HelloWorldImpl;

public class HelloWorldWSTest {
    private static HelloWorld helloWorld;

    @BeforeClass
    public static void setClient() throws IOException {
        helloWorld = HelloWorldImpl.getWSClient();
    }

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
