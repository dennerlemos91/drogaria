package com.denner.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.denner.drogaria.model.Fabricante;

public class FabricanteDao {

	private EntityManager manager;

	public FabricanteDao(EntityManager manager) {
		this.manager = manager;
	}
	
	/*Métodos*/
	
	public Fabricante porId(Long id){
		return manager.find(Fabricante.class, id);
	}
	
	public void guardar(Fabricante fabricante){
		this.manager.merge(fabricante);
	}

	public List<Fabricante> todos() {
		TypedQuery<Fabricante> query = manager.createQuery("from Fabricante",
				Fabricante.class);
		return query.getResultList();
	}
	
	public void remover(Fabricante fabricante){
		this.manager.remove(fabricante);
	}

}
