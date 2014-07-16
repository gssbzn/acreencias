package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Cliente;

public class ClienteDAOMemoryImpl implements ClienteDAO {
	
	private static List<Cliente> clientes = new ArrayList<Cliente>();
	private static Integer id = 0;
	
	@Override
	public Cliente create(Cliente cliente) {
		cliente.setId(++id);
		clientes.add(cliente);
		System.out.println(cliente);
		return cliente;
	}

	@Override
	public boolean update(Cliente cliente) {
		int index = clientes.indexOf(cliente);
		if(index < 0)
			return false;
		clientes.remove(index);
		if(index < clientes.size())
			clientes.add(index, cliente);
		else
			clientes.add(cliente);
		return true;
	}

	@Override
	public boolean remove(Cliente cliente) {		
		return clientes.remove(cliente);
	}

	@Override
	public Cliente find(Integer id) {
		Cliente cliente = null;
		for(Cliente cli : clientes){
			if(cli.getId() == id){
				cliente = cli;
				break;
			}
		}
		return cliente;
	}

	@Override
	public List<Cliente> findAll() {		
		return clientes;
	}
}
