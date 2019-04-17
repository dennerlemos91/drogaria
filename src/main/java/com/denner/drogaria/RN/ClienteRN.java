package com.denner.drogaria.RN;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import com.denner.drogaria.dao.ClienteDao;
import com.denner.drogaria.model.Cliente;
import com.denner.drogaria.util.JPAUtil;

public class ClienteRN implements Serializable {

	private static final long serialVersionUID = 1L;
	EntityManager manager = JPAUtil.getEntityManager();
	private ClienteDao clienteDao = new ClienteDao(manager);

	public void adicionar(Cliente cliente) {
		
			clienteDao.salvar(cliente);
		
	}
	
	public List<Cliente> listaCliente(){
		return clienteDao.todos();
	}
	
	public void deletar(Cliente cliente){
		cliente = clienteDao.porid(cliente.getId());
		if(cliente.getId() != null){
			clienteDao.remover(cliente);
		}
	}
	

}
