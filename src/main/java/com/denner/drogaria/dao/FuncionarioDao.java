package com.denner.drogaria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.denner.drogaria.model.Funcionario;

@SuppressWarnings("serial")
public class FuncionarioDao implements Serializable {

	private EntityManager manager;
	
	public FuncionarioDao (EntityManager manager){
		this.manager = manager;
	}

	public Funcionario porId(Long id) {
		return manager.find(Funcionario.class, id);
	}

	public void guardar(Funcionario funcionario) {
		manager.merge(funcionario);
	}


	public List<Funcionario> todos() {
		TypedQuery<Funcionario> query = manager.createQuery("from Funcionario",
				Funcionario.class);		
		return query.getResultList();
		
	}

	public void remover(Funcionario funcionario) {
				
			manager.remove(funcionario);
								
	}
	
	
}
