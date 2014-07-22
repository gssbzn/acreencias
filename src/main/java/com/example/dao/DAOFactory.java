package com.example.dao;

/**
 * 
 * @author guss
 *
 */
public class DAOFactory {
	
	public static ClienteDAO getClienteDAO() {
        return ClienteDAOMemoryImpl.getInstance();
    }
	
	public static CuentaDAO getCuentaDAO() {
        return CuentaDAOMemoryImpl.getInstance();
    }

	public static MovimientoDAO getMovimientodao() {
		return MovimientoDAOMemoryImpl.getInstance();
	}
	
	/*private void initialize(){
		for(int i=1; i<3; i++){
			Cliente cli = new Cliente(i, "Prueba " + i, "V-"+i);
			dao.create(cli);
		}
	}*/

}
