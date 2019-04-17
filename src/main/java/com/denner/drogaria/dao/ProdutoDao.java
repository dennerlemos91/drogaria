package com.denner.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.denner.drogaria.model.Produto;

public class ProdutoDao {

	
	private EntityManager manager;
	
	public ProdutoDao(EntityManager manager) {
		this.manager = manager;
	}
	
	public Produto porId(Long id){
		return manager.find(Produto.class, id);
	}
	
	public void guardar(Produto produto){
		this.manager.merge(produto);
	}

	public List<Produto> todos() {
		TypedQuery<Produto> query = manager.createQuery("from Produto",
				Produto.class);
		return query.getResultList();
	}
	
	public void remover(Produto produto){
		this.manager.remove(produto);
	}

}
