package com.denner.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.omnifaces.util.Messages;

import com.denner.drogaria.RN.ClienteRN;
import com.denner.drogaria.dao.ClienteDao;
import com.denner.drogaria.model.Cliente;
import com.denner.drogaria.util.JPAUtil;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Variáveis */
	private Cliente cliente;
	public List<Cliente> clientes = new ArrayList<>();

	/* GETS e SETS */

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	/* Métodos */

	public void novo() {
		cliente = new Cliente();
		System.out.println("Novo Cliente Criado.");
	}

	public void salvar() {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		try {
			ClienteDao clienteDao = new ClienteDao(manager);
			trx.begin();
			clienteDao.salvar(cliente);
			trx.commit();
			Messages.addGlobalInfo("Cliente " + cliente.getNome()
					+ " salvo com sucesso!!");
			clientes = clienteDao.todos();
			cliente = new Cliente();

		} catch (RuntimeException erro) {
			if (trx != null) {
				trx.rollback();
			}
			Messages.addGlobalError("Erro ao tentar salvar");
			erro.printStackTrace();
		}
	}

	public void carregarClientes() {
		ClienteRN clienteRN = new ClienteRN();
		try {
			clientes = clienteRN.listaCliente();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}

	public void editarCliente(ActionEvent evento) {
		cliente = (Cliente) evento.getComponent().getAttributes()
				.get("clienteSelecionado");
	}

	public void excluir(ActionEvent evento) {
		ClienteRN clienteRN = new ClienteRN();
		try {
			cliente = (Cliente) evento.getComponent().getAttributes()
					.get("clienteSelecionado");
			clienteRN.deletar(cliente);
			clientes = clienteRN.listaCliente();
			Messages.addGlobalInfo("Excluido com sucesso!");
			cliente = new Cliente();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir!");
			erro.printStackTrace();
		}
	}
}
