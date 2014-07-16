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
import com.example.dao.ClienteDAOFactory;
import com.example.model.Cliente;

public class ClientesResourceTest extends JerseyTest {
	
	private static ClienteDAO dao;

	@Override
    protected Application configure() {
        return new ResourceConfig(ClientesResource.class);
    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ClienteDAOFactory factory = new ClienteDAOFactory();
		dao = factory.createClienteDAO();
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
		int size = dao.findAll().size();
    	System.out.println("Show size=" + size);
	}

	@Test
	public void testIndex() {
		GenericType<Collection<Cliente>> genericRootElement = new GenericType<Collection<Cliente>>() {};
		Cliente cli = firstOrCreateCliente();
		Collection<Cliente> clientes = target().path("clientes").request().get(genericRootElement);
		for(Cliente cliente : clientes) {
			assertEquals(cli.getCedula(), cliente.getCedula());
			assertEquals(cli.getNombre(), cliente.getNombre());
			assertEquals(cli, cliente);
		}
		int size = dao.findAll().size();
    	System.out.println("Index size=" + size);
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
	public void testEdit() {
		firstOrCreateCliente();
		Cliente cli = new Cliente();
		cli.setCedula("V-2");
		cli.setNombre("Prueba 2");		
		target().path("clientes/1").request("application/xml").put(Entity.xml(cli), Cliente.class);
		Cliente cli2 = (Cliente)target().path("clientes/1").request().get(Cliente.class);
		assertEquals(cli.getCedula(), cli2.getCedula());
		assertEquals(cli.getNombre(), cli2.getNombre());		
	}

	@Test (expected=WebApplicationException.class)
	public void testRemove() {
		firstOrCreateCliente();
		target().path("clientes/1").request().delete();
		target().path("clientes/1").request().get(Cliente.class);
	}
	
	private Cliente firstOrCreateCliente() {
		Cliente c = null;
		try {
			c = (Cliente)target().path("clientes/1").request().get(Cliente.class);
		} catch (WebApplicationException ex) {
			c = new Cliente();
			c.setCedula("V-1");
			c.setNombre("Prueba 1");
			c = dao.create(c);
		}		
		return c;
	}

}
