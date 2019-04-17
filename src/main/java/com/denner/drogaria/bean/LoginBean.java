package com.denner.drogaria.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.denner.drogaria.dao.LoginDao;
import com.denner.drogaria.model.Funcionario;
import com.denner.drogaria.model.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private Usuario usuario;
	private Usuario usuarioLogado;
	
	public LoginBean() {
		usuario = new Usuario();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@PostConstruct
	public void inicializar(){
		usuario = new Usuario();
		usuario.setFuncionario(new Funcionario());
	}
	
	public void logar(){
		 	try {
		 		LoginDao loginDao = new LoginDao();
		 		usuarioLogado = loginDao.login(usuario.getNomeUsuario(), usuario.getSenha());
		 		if(usuarioLogado == null){
		 			Messages.addGlobalError("Nome de Usuário ou Senha Incorreto!");
		 			return;
		 		}
		 		
				Faces.redirect("./index.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public boolean permissoes(String permissoes){
		
			if(usuarioLogado.getTipoUsuario().equals(permissoes)){
				return true;
		}
		return false;
	}
}
