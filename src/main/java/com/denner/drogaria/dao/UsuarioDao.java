package com.denner.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.denner.drogaria.model.Usuario;

public class UsuarioDao {

	private EntityManager manager;

	public UsuarioDao(EntityManager manager) {
		this.manager = manager;
	}

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public void guardar(Usuario usuario) {
		this.manager.merge(usuario);
	}

	public List<Usuario> todos() {
		TypedQuery<Usuario> query = manager.createQuery("from Usuario",
				Usuario.class);
		return query.getResultList();
	}

	public void remover(Usuario usuario) {
		this.manager.remove(usuario);
	}

	public Usuario porNomeUsuario(String nomeUsusario) {
		Usuario usuario = null;
		try{
		usuario = manager.createQuery("from Usuario where lower(nomeUsuario) = :nomeUsuario", Usuario.class)
				.setParameter("nomeUsuario", nomeUsusario.toLowerCase()).getSingleResult();
		}catch(NoResultException e){
			//nenhum usuario encontrado com nome de usuario informado
		}
		return usuario;
	}
}
