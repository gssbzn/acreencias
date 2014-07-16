package com.example.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.model.Cliente;

public class ClienteDAOMemoryImplTest {
	
	private static ClienteDAO dao;

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
		Cliente cliente = new Cliente(1, "Prueba 1", "V-1");
		dao.create(cliente);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testCreate() {
		Cliente cli = new Cliente();
		cli.setCedula("V-2");
		cli.setNombre("Prueba 2");
		Cliente cli2 = dao.create(cli);
		assertEquals(cli.getNombre(), cli2.getNombre());
	}

	@Test
	public void testFind() {
		Cliente cli = new Cliente(1, "Prueba 1", "V-1");
		Cliente cliente = dao.find(1);
		assertEquals(cli, cliente);
	}

	@Test
	public void testFindAll() {
		Cliente cli = new Cliente(1, "Prueba 1", "V-1");
		for(Cliente cliente : dao.findAll()){
			assertEquals(cli, cliente);
		}
	}

}
