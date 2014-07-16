package com.example.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.example.dao.ClienteDAO;
import com.example.dao.ClienteDAOFactory;
import com.example.model.Cliente;

@Path("clientes")
public class ClientesResource {	
	private final ClienteDAO dao;
	
	public ClientesResource(){
		ClienteDAOFactory factory = new ClienteDAOFactory();
		dao = factory.createClienteDAO();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Cliente show(@PathParam("id") Integer id) {	
		System.out.println("SHOW");
		Cliente cliente = dao.find(id);
		if(cliente == null)
			throw new WebApplicationException(404);
		return cliente;
    }

    @GET    
    @Produces({"application/xml", "application/json"})
    public List<Cliente> index() {
    	System.out.println("INDEX");
        return dao.findAll();
    }
    
    @POST    
    @Consumes({"application/xml", "application/json"})
    public Response create(Cliente entity) {
    	System.out.println("CREATE");
        return Response.created(null).entity(dao.create(entity)).build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Cliente entity) {
    	System.out.println("PUT");
    	if(dao.find(id) == null)
    		throw new WebApplicationException(404);
    	entity.setId(id);
        dao.update(entity);
    }
    
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
    	System.out.println("DELETE");    	
        dao.remove(dao.find(id));
        return Response.ok().build();
    }

}
