package com.example.dao;

import java.util.List;

import com.example.model.Cuenta;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public interface CuentaDAO extends AbstractDAO<Cuenta, Integer> {
    public List<Cuenta> findCuentasCliente(Integer cliente_id);
}
