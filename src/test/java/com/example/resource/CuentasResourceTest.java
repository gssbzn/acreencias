package com.example.resource;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.dao.ClienteDAO;
import com.example.dao.CuentaDAO;
import com.example.factory.DAOFactory;
import com.example.factory.DAOFactory.DAOTYPE;
import com.example.model.Cliente;
import com.example.model.Cuenta;
import com.example.model.TipoCuenta;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class CuentasResourceTest extends JerseyTest {
	
	private static ClienteDAO clienteDao;
	private static CuentaDAO cuentaDao;

	@Override
    protected Application configure() {		
        return new ResourceConfig(CuentasResource.class);
    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOTYPE.MEMORYFACTORY);
		clienteDao = daoFactory.getClienteDAO();
		cuentaDao = daoFactory.getCuentaDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public final void testShow() {
		Cliente cli = firstOrCreateCliente();
		Cuenta cu = firstOrCreateCuenta(cli);
		Cuenta c = (Cuenta)target().path("clientes/"+cli.getId()+"/cuentas/"+cu.getId()).request().get(Cuenta.class);
		//System.out.println(c);
		assertEquals(cu, c);
	}

	@Test
	public final void testIndex() {
		Cliente cli = firstOrCreateCliente();
		firstOrCreateCuenta(cli);
		GenericType<Collection<Cuenta>> genericRootElement = new GenericType<Collection<Cuenta>>() {};
		Collection<Cuenta> cuentas = target().path("clientes/"+cli.getId()+"/cuentas/").request().get(genericRootElement);
		assertEquals(cuentaDao.findCuentasCliente(cli.getId()).size(), cuentas.size());
	}

	@Test
	public final void testCreate() {
		Cliente cli = firstOrCreateCliente();
		Cuenta cuenta = new Cuenta();
		cuenta.setTipo(TipoCuenta.ACREENCIA.getValue());
		
		Cuenta cu = (Cuenta) target().path("clientes/"+cli.getId()+"/cuentas").request("application/xml").post(Entity.xml(cuenta), Cuenta.class);
		assertEquals(cuenta.getTipo(), cu.getTipo());
		assertEquals(cuenta.getSaldo(), cu.getSaldo());
	}

	@Test
	public final void testUpdate() {
		Cliente cliente = firstOrCreateCliente();
		Cuenta cuenta = firstOrCreateCuenta(cliente);
		cuenta.setSaldo(new BigDecimal(100));
		target().path("clientes/" + cliente.getId()+"/cuentas/"+cuenta.getId()).request("application/xml").put(Entity.xml(cuenta), Cuenta.class);
		Cuenta cu = (Cuenta)target().path("clientes/" + cliente.getId()+"/cuentas/"+cuenta.getId()).request().get(Cuenta.class);
		assertEquals(cuenta.getSaldo(), cu.getSaldo());
	}

	@Test (expected=WebApplicationException.class)
	public final void testRemove() {
		Cliente cliente = firstOrCreateCliente();
		Cuenta cuenta = firstOrCreateCuenta(cliente);
		target().path("clientes/" + cliente.getId() + "/cuentas/" + cuenta.getId()).request().delete();
		target().path("clientes/" + cliente.getId() + "/cuentas/" + cuenta.getId()).request().get(Cuenta.class);
	}
	
	private Cliente firstOrCreateCliente() {
		Cliente c = clienteDao.first();
		if (c == null){
			c = new Cliente();
			c.setCedula("V-1");
			c.setNombre("Prueba 1");
			c = clienteDao.create(c);
		}
		return c;
	}
	
	private Cuenta firstOrCreateCuenta(Cliente cliente) {
		Cuenta c = cuentaDao.first();
		if (c == null){
			c = new Cuenta();
			c.setTipo(TipoCuenta.ACREENCIA.getValue());
			c.setCliente(cliente);
			c = cuentaDao.create(c);
		}
		return c;
	}

}
