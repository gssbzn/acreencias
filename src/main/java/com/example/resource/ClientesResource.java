package com.example.resource;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.dao.ClienteDAO;
import com.example.dao.DAOFactory;
import com.example.model.Cliente;

@Path("clientes")
public class ClientesResource {	
	private final ClienteDAO dao;
	
	private static final Logger logger = Logger.getLogger(ClientesResource.class.toString());
	
	public ClientesResource(){		
		dao = DAOFactory.getClienteDAO();
	}
	
	@GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cliente show(@PathParam("id") Integer id) {	
		logger.info("SHOW");
		Cliente cliente = dao.find(id);
		if(cliente == null)
			throw new WebApplicationException(404);
		return cliente;
    }

    @GET    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> index() {
    	//logger.info("INDEX");    	
        return dao.findAll();
    }
    
    @POST    
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Cliente entity) {
    	logger.info("CREATE");
    	Cliente cli = (Cliente) dao.create(entity);
    	System.out.println(cli);
        return Response.created(null).entity(cli).build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(@PathParam("id") Integer id, Cliente entity) {
    	logger.info("PUT");
    	if(dao.find(id) == null)
    		throw new WebApplicationException(404);
    	entity.setId(id);
        dao.update(entity);
    }
    
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
    	logger.info("DELETE");    	
        dao.remove(dao.find(id));
        return Response.ok().build();
    }

}
