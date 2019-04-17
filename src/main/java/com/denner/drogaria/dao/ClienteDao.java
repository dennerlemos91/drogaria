package com.denner.drogaria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.denner.drogaria.model.Cliente;
import com.denner.drogaria.util.JPAUtil;

public class ClienteDao implements Serializable {

	private static final long serialVersionUID = 1L;

	EntityManager manager = JPAUtil.getEntityManager();

	public ClienteDao(EntityManager manager) {
		this.manager = manager;
	}

	public void salvar(Cliente cliente) {
		manager.merge(cliente);
	}

	public void remover(Cliente cliente) {
		manager.remove(cliente);
	}

	public List<Cliente> todos() {
		TypedQuery<Cliente> query = manager.createQuery("from Cliente",
				Cliente.class);
		return query.getResultList();
	}

	public Cliente porid(Long id) {
		return manager.find(Cliente.class, id);
	}

}
