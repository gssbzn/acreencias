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
import com.example.dao.CuentaDAO;
import com.example.dao.DAOFactory;
import com.example.model.Cliente;
import com.example.model.Cuenta;

/**
 * 
 * @author Gustavo Bazan
 *
 */
@Path("clientes/{cliente_id}/cuentas")
public class CuentasResource {

    private final ClienteDAO clienteDao;
    private final CuentaDAO cuentaDao;

    private static final Logger logger = Logger.getLogger(ClientesResource.class.toString());
    
    @Context
    protected UriInfo uriInfo;

    public CuentasResource(){		
        clienteDao = DAOFactory.getClienteDAO();
        cuentaDao = DAOFactory.getCuentaDAO();
    }

    /**
     * 
     * @param cliente_id
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cuenta show(@PathParam("cliente_id") Integer cliente_id, @PathParam("id") Integer id) {	
        logger.info("SHOW");
        Cliente cliente = clienteDao.find(cliente_id);		
        Cuenta cuenta = cuentaDao.find(id);
        if(cliente == null || cuenta == null)
            throw new WebApplicationException(404);
        return cuenta;
    }

    @GET    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cuenta> index(@PathParam("cliente_id") Integer cliente_id) {
    	logger.info("INDEX");
    	Cliente cliente = clienteDao.find(cliente_id);    	
        if(cliente == null)
            throw new WebApplicationException(404);
        return cuentaDao.findCuentasCliente(cliente_id);
    }
    
    @POST    
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(@PathParam("cliente_id") Integer cliente_id, Cuenta entity) {
    	logger.info("CREATE");
    	Cliente cliente = clienteDao.find(cliente_id);
    	if(cliente == null)
            throw new WebApplicationException(404);
    	
        entity.setCliente(cliente);
    	Cuenta cu = (Cuenta) cuentaDao.create(entity);
    	UriBuilder ub = uriInfo.getAbsolutePathBuilder();
    	URI userUri = ub.path(cu.getId().toString()).build();
        return Response.created(userUri).entity(cu).build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(@PathParam("cliente_id") Integer cliente_id, @PathParam("id") Integer id, Cuenta entity) {
    	logger.info("PUT");
    	if(cuentaDao.find(id) == null)
            throw new WebApplicationException(404);
    	
        entity.setId(id);
        cuentaDao.update(entity);
    }
    
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
    	logger.info("DELETE");    	
        cuentaDao.remove(cuentaDao.find(id));
        return Response.ok().build();
    }
}