package com.example.resource;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.dao.ClienteDAO;
import com.example.factory.DAOFactory;
import com.example.factory.DAOFactory.DAOTYPE;
import com.example.model.Cliente;

/**
 * Servicio REST para clientes
 * @author Gustavo Bazan
 *
 */
@Path("clientes")
public class ClientesResource {	
	/** Logger */
    private static final Logger logger = Logger.getLogger(ClientesResource.class.getCanonicalName());
    /** DAO Cliente */
	private final ClienteDAO dao;

    @Context
    protected UriInfo uriInfo;
    
    public ClientesResource(){
    	DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOTYPE.MEMORYFACTORY);
        dao = daoFactory.getClienteDAO();
    }

    /**
     * Consultar cliente
     * @param id Id del cliente
     * @return Cliente solicitado
     * @throws WebApplicationException con 404 si no existe el cliente
     */
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
    	logger.info("INDEX");    	
        return dao.findAll();
    }
    
    @POST    
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Cliente entity) {
    	logger.info("CREATE");
    	Cliente cli = (Cliente) dao.create(entity);
    	UriBuilder ub = uriInfo.getAbsolutePathBuilder();
    	URI userUri = ub.path(cli.getId().toString()).build();
    
        return Response.created(userUri).entity(cli).build();
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
