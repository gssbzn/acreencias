package com.example.dao;


public class ClienteDAOFactory {
	
	public ClienteDAO createClienteDAO() {
        return new ClienteDAOMemoryImpl();
    }
	
	/*private void initialize(){
		for(int i=1; i<3; i++){
			Cliente cli = new Cliente(i, "Prueba " + i, "V-"+i);
			dao.create(cli);
		}
	}*/

}
