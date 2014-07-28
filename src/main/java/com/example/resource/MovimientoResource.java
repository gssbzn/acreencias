package com.example.resource;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.dao.ClienteDAO;
import com.example.dao.CuentaDAO;
import com.example.dao.DAOFactory;
import com.example.dao.MovimientoDAO;
import com.example.model.Cliente;
import com.example.model.Cuenta;
import com.example.model.Movimiento;

@Path("clientes/{cliente_id}/cuentas/{cuenta_id}/movimientos")
public class MovimientoResource {
	
	private final ClienteDAO clienteDao;
	private final CuentaDAO cuentaDao;
	private final MovimientoDAO movimientoDao;
	
	private static final Logger logger = Logger.getLogger(MovimientoResource.class.toString());

	@Context
	protected UriInfo uriInfo;
	
    public MovimientoResource(){		
        movimientoDao = DAOFactory.getMovimientoDAO();
        clienteDao = DAOFactory.getClienteDAO();
        cuentaDao = DAOFactory.getCuentaDAO();
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Movimiento show(@PathParam("cuenta_id") Integer cuenta_id, @PathParam("id") Integer id) {	
		logger.info("SHOW");
		Movimiento movimiento = movimientoDao.find(id);
		if(movimiento == null)
			throw new WebApplicationException(404);
		return movimiento;
    }
    
    @GET    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Movimiento> index(@PathParam("cliente_id") Integer cliente_id, @PathParam("cuenta_id") Integer cuenta_id) {
    	logger.info("SHOW-ALL");    	
        return movimientoDao.findMovimientosCuenta(cuenta_id);
    }
    
    @POST    
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(@PathParam("cliente_id") Integer cliente_id, @PathParam("cuenta_id") Integer cuenta_id, Movimiento entity) {
    	logger.info("CREATE");
    	Cliente cliente = clienteDao.find(cliente_id);		
        Cuenta cuenta = cuentaDao.find(cuenta_id);
        if(cliente == null || cuenta == null)
            throw new WebApplicationException(404);
    	
        entity.setCuenta(cuenta);
    	Movimiento mov = (Movimiento) movimientoDao.create(entity);   	
    	logger.info("Nuevo Movimiento creado: Monto: "+mov.getMonto()+" Tipo: "+mov.getTipo()+" Cuenta: "+mov.getCuenta().getId());
    	cuentaDao.actualizarCuenta(mov);
    	logger.info("Saldo Actualizado: "+mov.getCuenta().getSaldo()+" Cuenta id: "+mov.getCuenta().getId());
    	
    	UriBuilder ub = uriInfo.getAbsolutePathBuilder();
    	URI movUri = ub.path(mov.getId().toString()).build();
        return Response.created(movUri).entity(mov).build();
    }
    
    @POST
    @Path("async")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createAsync(@Suspended final AsyncResponse asyncResponse, @PathParam("cliente_id") Integer cliente_id, @PathParam("cuenta_id") Integer cuenta_id, final Movimiento entity) {
    	logger.info("CREATE");
    	asyncResponse.setTimeoutHandler(new TimeoutHandler() {    		 
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
    	asyncResponse.setTimeout(15, TimeUnit.SECONDS);
    	final Cliente cliente = clienteDao.find(cliente_id);		
        final Cuenta cuenta = cuentaDao.find(cuenta_id);
        if(cliente == null || cuenta == null)
            throw new WebApplicationException(404);
    	new Thread(new Runnable() {
    		 
            @Override
            public void run() {
                asyncResponse.resume(veryExpensiveOperation());
            }
     
            private Response veryExpensiveOperation() {
            	entity.setCuenta(cuenta);
            	Movimiento mov = (Movimiento) movimientoDao.create(entity);
            	cuentaDao.actualizarCuenta(mov);
            	UriBuilder ub = uriInfo.getAbsolutePathBuilder();
             	URI movUri = ub.path(mov.getId().toString()).build();
             	return Response.created(movUri).entity(mov).build();
            }
        }).start();
    }

}
