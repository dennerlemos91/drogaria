package com.denner.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.omnifaces.util.Messages;

import com.denner.drogaria.RN.UsuarioRN;
import com.denner.drogaria.dao.FuncionarioDao;
import com.denner.drogaria.dao.UsuarioDao;
import com.denner.drogaria.model.Funcionario;
import com.denner.drogaria.model.TipoUsuario;
import com.denner.drogaria.model.Usuario;
import com.denner.drogaria.util.JPAUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private List<SelectItem> funcionarioSelecionado;
	private List<Funcionario> funcionarios;
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	/*Métodos*/
	
	public TipoUsuario[] getTipoUsuarios(){
		return TipoUsuario.values();
	}
	

	public List<SelectItem> getFuncionarioSelecionado() {
		EntityManager manager = JPAUtil.getEntityManager();
		if (funcionarioSelecionado == null) {
			funcionarioSelecionado = new ArrayList<SelectItem>();

			FuncionarioDao funcionarioDao = new FuncionarioDao(manager);

			List<Funcionario> funcionarios = funcionarioDao.todos();

			if (funcionarios != null && !funcionarios.isEmpty()) {
				SelectItem item;
				for (Funcionario funcionario : funcionarios) {
					item = new SelectItem(funcionario, funcionario.getNome());
					funcionarioSelecionado.add(item);
				}
			}

		}

		return funcionarioSelecionado;
	}
	
		
	public void novo() {
		usuario = new Usuario();
	}
	
	

	public void salvar() {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			UsuarioRN usuarioRN = new UsuarioRN(new UsuarioDao(manager));
			usuarioRN.adicionar(usuario);
			
			Messages.addGlobalInfo("" + usuario.getNomeUsuario()
					+ " Salvo com sucesso!!!");
			trx.commit();
			listaUsuarios = usuarioRN.todosUsuarios();
			usuario = new Usuario();
		} catch (RuntimeException erro) {
			trx.rollback();
			Messages.addGlobalError(erro.getMessage());
			erro.printStackTrace();
		}
	}
	
	public void inicializar(){
		EntityManager manager = JPAUtil.getEntityManager();
		UsuarioRN usuarioRN = new UsuarioRN(new UsuarioDao(manager));
		listaUsuarios = usuarioRN.todosUsuarios();
	}
	
	public void alterar(ActionEvent evento){
		usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
	}
	
	
	public void excluir(ActionEvent evento) {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			trx.begin();
			UsuarioRN usuarioRN = new UsuarioRN(new UsuarioDao(manager));
			usuarioRN.remover(usuario);
			
			Messages.addGlobalInfo("" + usuario.getNomeUsuario()
					+ " Excluido com sucesso");
			trx.commit();
			listaUsuarios = usuarioRN.todosUsuarios();
			usuario = new Usuario();
		} catch (RuntimeException erro) {
			trx.rollback();
			Messages.addGlobalError(erro.getMessage());
			erro.printStackTrace();
		}
	}
	

}
