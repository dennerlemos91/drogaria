package com.denner.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.omnifaces.util.Messages;

import com.denner.drogaria.Exceptions.NegocioExcepion;
import com.denner.drogaria.RN.FuncionarioRN;
import com.denner.drogaria.dao.FuncionarioDao;
import com.denner.drogaria.model.Funcionario;
import com.denner.drogaria.util.JPAUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {

	private Funcionario funcionario;
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	FuncionarioRN funcionarioRN;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@PostConstruct
	public void novo()
	{
		funcionario = new Funcionario();
	}
	
	public void Salvar(){
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		try {
			trx.begin();
			FuncionarioRN funcionarioRN = new FuncionarioRN(new FuncionarioDao(
					manager));
			funcionarioRN.Salvar(funcionario);
			funcionarios = funcionarioRN.listar();
			Messages.addGlobalInfo("Funcionario '" + funcionario.getNome()
					+ "' salvo com sucesso!");
			funcionario = new Funcionario();
			trx.commit();
		} catch (NegocioExcepion erro) {
			trx.rollback();
			Messages.addGlobalError(erro.getMessage());
			erro.printStackTrace();
		} finally {
			manager.close();
		}

	}
	
	public void carregarFuncionarios(){
		EntityManager manager = JPAUtil.getEntityManager();
		try {
			FuncionarioDao funcionarioDao = new FuncionarioDao(manager);
			this.funcionarios = funcionarioDao.todos();
		} finally {
			manager.close();
		}
	}
	
	public void alterar(ActionEvent evento){
		funcionario = (Funcionario)evento.getComponent().getAttributes().get("produtoSelecionado");
	}
	
	
	public void excluir(ActionEvent evento){
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes()
					.get("produtoSelecionado");
			trx.begin();
			FuncionarioRN excluir = new FuncionarioRN(
					new FuncionarioDao(manager));
			excluir.remover(funcionario);
			funcionarios = excluir.listar();

			Messages.addGlobalInfo("Funcionario  " + funcionario.getNome()
					+ " ' excluido com sucesso!");
			funcionario = new Funcionario();
			trx.commit();

		} catch (RuntimeException erro) {
			if (trx != null) {
				trx.rollback();
			}
			Messages.addFlashGlobalError("Não é possivel excluir o Fabricante: '"
					+ funcionario.getNome() + "'");
			erro.printStackTrace();

		} finally {
			manager.close();
		}
	}
	
	

}
