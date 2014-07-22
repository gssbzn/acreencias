package com.example.resource;

import static org.junit.Assert.assertEquals;

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
import com.example.dao.DAOFactory;
import com.example.model.Cliente;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class ClientesResourceTest extends JerseyTest {
	
	private static ClienteDAO dao;

	@Override
    protected Application configure() {		
        return new ResourceConfig(ClientesResource.class);
    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = DAOFactory.getClienteDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
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
	public void testShow() {
		Cliente cli = firstOrCreateCliente();
		//int status = b.get().getStatus();
		//System.out.println("status=" + status);
		Cliente c = (Cliente)target().path("clientes/1").request().get(Cliente.class);
		//System.out.println(c);
		assertEquals(cli, c);
	}

	@Test
	public void testIndex() {
		GenericType<Collection<Cliente>> genericRootElement = new GenericType<Collection<Cliente>>() {};
		
		Collection<Cliente> clientes = target().path("clientes").request().get(genericRootElement);
		
		/*for(Cliente cliente : clientes) {
			System.out.println(cli);
			System.out.println(cliente);
			//assertEquals(cli.getCedula(), cliente.getCedula());
			//assertEquals(cli.getNombre(), cliente.getNombre());
			//assertEquals(cli, cliente);
		}*/
		assertEquals(dao.findAll().size(), clientes.size());
	}

	@Test
	public void testCreate() {
		Cliente cli = new Cliente();
		cli.setCedula("V-1");
		cli.setNombre("Prueba 1");		
		Cliente cli2 = (Cliente) target().path("clientes").request("application/xml").post(Entity.xml(cli), Cliente.class);
		assertEquals(cli.getCedula(), cli2.getCedula());
		assertEquals(cli.getNombre(), cli2.getNombre());
	}

	@Test
	public void testUpdate() {
		Cliente cliente = firstOrCreateCliente();
		Cliente cli = new Cliente();
		cli.setCedula("V-2");
		cli.setNombre("Prueba 2");		
		target().path("clientes/" + cliente.getId()).request("application/xml").put(Entity.xml(cli), Cliente.class);
		Cliente cli2 = (Cliente)target().path("clientes/" + cliente.getId()).request().get(Cliente.class);
		assertEquals(cli.getCedula(), cli2.getCedula());
		assertEquals(cli.getNombre(), cli2.getNombre());
	}

	@Test (expected=WebApplicationException.class)
	public void testRemove() {
		Cliente cliente = firstOrCreateCliente();
		target().path("clientes/" + cliente.getId()).request().delete();
		target().path("clientes/" + cliente.getId()).request().get(Cliente.class);
	}
	
	private Cliente firstOrCreateCliente() {
		Cliente c = dao.first();
		if (c == null){
			c = new Cliente();
			c.setCedula("V-1");
			c.setNombre("Prueba 1");
			c = dao.create(c);
		}
		return c;
	}

}
