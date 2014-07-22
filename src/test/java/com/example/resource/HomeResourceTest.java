package com.example.resource;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.example.resource.HomeResource;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class HomeResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(HomeResource.class);
    }

    /**
     * Test to see that the message "Hello World!" is sent in the response.
     */
    @Test
    public void testGetHello() {
        final String responseMsg = target().path("/").request().get(String.class);

        assertEquals("Hello World!", responseMsg);
    }
    
    /**
     * Test to see that the message "Hello World!" is sent in the response.
     */
    @Test
    public void testGetHelloMessage() {
        final String responseMsg = target("hello").path("/Gustavo").request().get(String.class);

        assertEquals("Hello Gustavo!", responseMsg);
    }
}
