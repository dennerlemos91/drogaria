package com.denner.drogaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.omnifaces.util.Messages;

import com.denner.drogaria.dao.ClienteDao;
import com.denner.drogaria.dao.FuncionarioDao;
import com.denner.drogaria.dao.ProdutoDao;
import com.denner.drogaria.dao.VendaDao;
import com.denner.drogaria.model.Cliente;
import com.denner.drogaria.model.Funcionario;
import com.denner.drogaria.model.ItemVenda;
import com.denner.drogaria.model.Produto;
import com.denner.drogaria.model.Venda;
import com.denner.drogaria.util.JPAUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private Venda venda;
	private List<Produto> listaProdutos;
	private List<ItemVenda> itensVendas;
	private List<SelectItem> clienteSelecionado;
	private List<SelectItem> funcionarioSelecionado;

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<ItemVenda> getItensVendas() {
		return itensVendas;
	}

	public void setItensVendas(List<ItemVenda> itensVendas) {
		this.itensVendas = itensVendas;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<SelectItem> getClienteSelecionado() {
		EntityManager manager = JPAUtil.getEntityManager();
		if (clienteSelecionado == null) {
			clienteSelecionado = new ArrayList<SelectItem>();

			ClienteDao clienteDao = new ClienteDao(manager);

			List<Cliente> clientes = clienteDao.todos();

			if (clientes != null && !clientes.isEmpty()) {
				SelectItem item;
				for (Cliente cliente : clientes) {
					item = new SelectItem(cliente, cliente.getNome());
					clienteSelecionado.add(item);
				}
			}
		}
		return clienteSelecionado;
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

	@PostConstruct
	public void inicializar() {
		EntityManager manager = JPAUtil.getEntityManager();
		try {
			venda = new Venda();
			venda.setDataVenda(new Date());
			venda.setValorTotal(new BigDecimal("0.00"));
			
			itensVendas = new ArrayList<ItemVenda>();

			ProdutoDao produtoDao = new ProdutoDao(manager);
			this.listaProdutos = produtoDao.todos();

		} finally {
			manager.close();
		}
	}


	public void adicionarItem(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes()
				.get("produtoSelecionado");

		int achou = -1;

		for (int i = 0; i < itensVendas.size(); i++) {
			if (itensVendas.get(i).getProduto().equals(produto)) {
				achou = i;
			}
		}

		if (achou == -1) {

			ItemVenda itemVenda = new ItemVenda();

			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(1);
			itemVenda.setValorParcial(produto.getValor());

			itensVendas.add(itemVenda);
		} else {
			ItemVenda itemVenda = itensVendas.get(achou);
			itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
			itemVenda.setValorParcial(produto.getValor().multiply(
					new BigDecimal(itemVenda.getQuantidade())));
		}

		caucularTotal();
	}

	public void removerItem(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes()
				.get("itemSelecionado");

		int achou = -1;

		for (int i = 0; i < itensVendas.size(); i++) {
			if (itensVendas.get(i).getProduto().equals(itemVenda.getProduto())) {
				achou = i;
			}
		}

		if (achou > -1) {
			itensVendas.remove(achou);
			System.out.println("Removeu");
		}

		caucularTotal();

	}

	public void caucularTotal() {
		venda.setValorTotal(new BigDecimal("0.00"));

		for (int i = 0; i < itensVendas.size(); i++) {
			ItemVenda itemVenda = itensVendas.get(i);
			venda.setValorTotal(venda.getValorTotal().add(
					itemVenda.getValorParcial()));
		}
	}

	public void finalizarVenda() {
		venda.setDataVenda(new Date());
	}

	public void salvarVenda() {
		try {

			if (venda.getValorTotal().signum() == 0) {
				Messages.addGlobalInfo("Informa ao menos um Item para a venda!");
				return;
			}else{
				VendaDao vendaDao = new VendaDao();
				vendaDao.adicionar(venda, itensVendas);

				Messages.addGlobalInfo("Venda salva com sucesso!");
				
				inicializar();
			
			}

			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Não foi possivel salvar a venda");
			erro.printStackTrace();
		}
	}

}
