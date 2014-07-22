package com.example.dao;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class DAOFactory {
	
    public static ClienteDAO getClienteDAO() {
        return ClienteDAOMemoryImpl.getInstance();
    }
	
    public static CuentaDAO getCuentaDAO() {
        return CuentaDAOMemoryImpl.getInstance();
    }

    public static MovimientoDAO getMovimientoDAO() {
        return MovimientoDAOMemoryImpl.getInstance();
    }
	
    /*private void initialize(){
        for(int i=1; i<3; i++){
            Cliente cli = new Cliente(i, "Prueba " + i, "V-"+i);
            dao.create(cli);
        }
    }*/

}
