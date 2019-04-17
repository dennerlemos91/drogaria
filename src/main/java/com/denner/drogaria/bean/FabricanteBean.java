package com.denner.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.omnifaces.util.Messages;

import com.denner.drogaria.Exceptions.NegocioExcepion;
import com.denner.drogaria.RN.FabricantesRN;
import com.denner.drogaria.dao.FabricanteDao;
import com.denner.drogaria.model.Fabricante;
import com.denner.drogaria.util.JPAUtil;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class FabricanteBean implements Serializable {

	private Fabricante fabricante = new Fabricante();
	private List<Fabricante> todosfabricantes = new ArrayList<Fabricante>();
	

	public FabricanteBean() {
		fabricante = new Fabricante();
		todosfabricantes = new ArrayList<Fabricante>();
	}
	
	/* Metodos get e Sets */

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<Fabricante> getTodosfabricantes() {
		return todosfabricantes;
	}

	public void setTodosfabricantes(List<Fabricante> todosfabricantes) {
		this.todosfabricantes = todosfabricantes;
	}

	
	/* Metodos Gerais */

	public void novo() {
		fabricante = new Fabricante();
	}

	public void carregarFabricantes() {
		EntityManager manager = JPAUtil.getEntityManager();
		try {
			FabricanteDao fabricanteDao = new FabricanteDao(manager);
			this.todosfabricantes = fabricanteDao.todos();
		} finally {
			manager.close();
		}
	}

	public void salvar() {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		try {
			trx.begin();
			FabricantesRN cadastrar = new FabricantesRN(new FabricanteDao(
					manager));
			cadastrar.Salvar(fabricante);
			todosfabricantes = cadastrar.listar();
			Messages.addGlobalInfo("Fabricante '" + fabricante.getDescricao()
					+ "' salvo com sucesso!");
			fabricante = new Fabricante();
			trx.commit();
		} catch (NegocioExcepion erro) {
			trx.rollback();
			Messages.addGlobalError(erro.getMessage());
			erro.printStackTrace();
		} finally {
			manager.close();
		}

	}

	public void excluir(ActionEvent evento) {

		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			fabricante = (Fabricante) evento.getComponent().getAttributes()
					.get("fabricanteSelecionado");
			trx.begin();
			FabricantesRN excluir = new FabricantesRN(
					new FabricanteDao(manager));
			excluir.remover(fabricante);
			todosfabricantes = excluir.listar();

			Messages.addGlobalInfo("Fabricante  " + fabricante.getDescricao()
					+ " ' excluido com sucesso!");
			fabricante = new Fabricante();
			trx.commit();

		} catch (RuntimeException erro) {
			if (trx != null) {
				trx.rollback();
			}
			Messages.addFlashGlobalError("Não é possivel excluir o Fabricante: '"
					+ fabricante.getDescricao() + "'");
			erro.printStackTrace();

		} finally {
			manager.close();
		}
	}

	public void editar(ActionEvent evento) {
		fabricante = (Fabricante) evento.getComponent().getAttributes()
				.get("fabricanteSelecionado");
		
	}

}
