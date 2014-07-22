package com.example.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "/" path)
 * @author Gustavo Bazan
 */
@Path("/")
public class HomeResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Hello World!";
    }
    
    @GET
    @Path("/hello/{param}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello(@PathParam(value = "param") String message) {
        return "Hello " + message + "!";
    }
    
}
