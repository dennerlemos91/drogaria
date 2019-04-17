package com.denner.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.omnifaces.util.Messages;

import com.denner.drogaria.Exceptions.NegocioExcepion;
import com.denner.drogaria.RN.ProdutoRN;
import com.denner.drogaria.dao.FabricanteDao;
import com.denner.drogaria.dao.ProdutoDao;
import com.denner.drogaria.model.CategoriaProduto;
import com.denner.drogaria.model.Fabricante;
import com.denner.drogaria.model.FormulaProduto;
import com.denner.drogaria.model.Produto;
import com.denner.drogaria.model.TipoProduto;
import com.denner.drogaria.util.JPAUtil;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	private Produto produto;	
	private List<Produto> produtos = new ArrayList<Produto>();
	private List<SelectItem> fabricanteSelcionado;

	public ProdutoBean() {
		produto = new Produto();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	// carrega o selectOneMenu do tipo
	public TipoProduto[] gettipoProdutos() {
		return TipoProduto.values();
	}

	// carrega o selectOneMenu das categorias
	public CategoriaProduto[] getcategoriaProduto() {
		return CategoriaProduto.values();
	}
	
	// carrega o selectOneMenu da formula
	public FormulaProduto[] getfomulaProduto(){
		return FormulaProduto.values();
	}
	
	// Carrega o SelectOneMenu do Fabricante
	public List<SelectItem> getFabricanteSelecionado() {
		EntityManager manager = JPAUtil.getEntityManager();
		if (fabricanteSelcionado == null) {
			fabricanteSelcionado = new ArrayList<SelectItem>();

			FabricanteDao fabricanteRepository = new FabricanteDao(manager);

			List<Fabricante> fabricantes = fabricanteRepository.todos();

			if (fabricantes != null && !fabricantes.isEmpty()) {
				SelectItem item;
				for (Fabricante fabricante : fabricantes) {
					item = new SelectItem(fabricante, fabricante.getDescricao());
					fabricanteSelcionado.add(item);
				}
			}
		}
		return fabricanteSelcionado;
	}

	@PostConstruct
	public void novo() {
		produto = new Produto();
	}
	
	public void carregarProdutos() {
		EntityManager manager = JPAUtil.getEntityManager();
		try {
			ProdutoDao produtoDao = new ProdutoDao(manager);
			this.produtos = produtoDao.todos();
		} finally {
			manager.close();
		}
	}

	public void salvar() {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			ProdutoRN cadastrar = new ProdutoRN(new ProdutoDao(manager));
			cadastrar.adicionar(produto);

			Messages.addGlobalInfo("" + produto.getDescricao()
					+ " Salvo com sucesso!!!");
			trx.commit();
			produtos = cadastrar.todosProdutos();
			produto = new Produto();
		} catch (NegocioExcepion erro) {
			trx.rollback();
			Messages.addGlobalError(erro.getMessage());
			erro.printStackTrace();
		}
	}
	
	public void alterar(ActionEvent evento){
		produto = (Produto)evento.getComponent().getAttributes().get("produtoSelecionado");
	}
	
	
	public void excluir(ActionEvent evento){
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();
		try{
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			trx.begin();
			ProdutoRN produtoRN = new ProdutoRN(new ProdutoDao(manager));
			produtoRN.remover(produto);
			produtos = produtoRN.todosProdutos();
			Messages.addGlobalInfo(produto.getDescricao()+" Removido com sucesso!");
			produto = new Produto();
			trx.commit();
		}catch(RuntimeException erro){
			Messages.addGlobalError("Não é possivel excluir o produto:"+produto.getDescricao());
			erro.printStackTrace();
		}
	}

}
