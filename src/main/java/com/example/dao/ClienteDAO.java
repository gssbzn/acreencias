package com.example.dao;

import java.util.List;

import com.example.model.Cliente;

public interface ClienteDAO {
	
	public Cliente create(Cliente cliente);

    public boolean update(Cliente cliente);

    public boolean remove(Cliente cliente);

    public Cliente find(Integer id);

    public List<Cliente> findAll();

}
