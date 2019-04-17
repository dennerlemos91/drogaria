package com.denner.drogaria.RN;

import java.util.ArrayList;
import java.util.List;

import com.denner.drogaria.dao.UsuarioDao;
import com.denner.drogaria.model.Usuario;

public class UsuarioRN {
	
	private UsuarioDao usuarioDao;
	private List<Usuario> todos = new ArrayList<Usuario>();

	public UsuarioRN (UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	public void adicionar(Usuario usuario){
		usuarioDao.guardar(usuario);
	}
	
	public List<Usuario> todosUsuarios(){
		todos = usuarioDao.todos();
		return todos;
	}

	public void remover(Usuario usuario) {
		usuario = usuarioDao.porId(usuario.getId());
		if(usuario.getId() == null){
			throw new RuntimeException("Procuto Null");
		}
		usuarioDao.remover(usuario);
	}

}
