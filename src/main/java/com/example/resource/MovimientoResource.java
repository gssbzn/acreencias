package com.example.resource;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.dao.CuentaDAO;
import com.example.dao.DAOFactory;
import com.example.dao.MovimientoDAO;
import com.example.model.Cuenta;
import com.example.model.Movimiento;

@Path("clientes/{cliente_id}/cuentas/{cuentas_id}/movimientos")
public class MovimientoResource {
	private final MovimientoDAO dao;
	private final CuentaDAO cuentaDAO;
	
	private static final Logger logger = Logger.getLogger(MovimientoResource.class.toString());

    public MovimientoResource(){		
        dao = DAOFactory.getMovimientoDAO();
        cuentaDAO = DAOFactory.getCuentaDAO();
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Movimiento show(@PathParam("cuenta_id") Integer cuenta_id, @PathParam("id") Integer id) {	
		logger.info("SHOW");
		Movimiento movimiento = dao.find(id);
		if(movimiento == null)
			throw new WebApplicationException(404);
		return movimiento;
    }
    
    @GET    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Movimiento> index(@PathParam("cliente_id") Integer cliente_id, @PathParam("cuenta_id") Integer cuenta_id) {
    	logger.info("SHOW-ALL");    	
        return dao.findMovimientosCuenta(cuenta_id);
    }
    
    @POST    
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(@PathParam("cuenta_id") Integer cuenta_id, Movimiento entity) {
    	logger.info("CREATE");
    	Cuenta cuenta = cuentaDAO.find(cuenta_id);
    	entity.setCuenta(cuenta);
    	Movimiento mov = (Movimiento) dao.create(entity);   	
    	System.out.println("Nuevo Movimiento creado: Monto: "+mov.getMonto()+" Tipo: "+mov.getTipo()+" Cuenta: "+mov.getCuenta().getId());
    	cuentaDAO.actualizarCuenta(mov);
    	System.out.println("Saldo Actualizado: "+mov.getCuenta().getSaldo()+" Cuenta id: "+mov.getCuenta().getId());
        return Response.created(null).entity(mov).build();
    }
    


}
