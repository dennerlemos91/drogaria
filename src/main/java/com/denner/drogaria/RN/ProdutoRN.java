package com.denner.drogaria.RN;

import java.util.ArrayList;
import java.util.List;

import com.denner.drogaria.Exceptions.NegocioExcepion;
import com.denner.drogaria.dao.ProdutoDao;
import com.denner.drogaria.model.Produto;

public class ProdutoRN {
	
	private ProdutoDao produtoDao;
	private List<Produto> todos = new ArrayList<Produto>();

	public ProdutoRN(ProdutoDao produtos) {
		this.produtoDao = produtos;
	}
	
	public void adicionar(Produto produto)throws NegocioExcepion{
		if(produto == null){
			throw new NegocioExcepion("Deu Pala");
		}
		produtoDao.guardar(produto);
	}
	
	public List<Produto> todosProdutos(){
		todos = produtoDao.todos();
		return todos;
	}

	public void remover(Produto produto) {
		produto = produtoDao.porId(produto.getId());
		if(produto.getId() == null){
			throw new RuntimeException("Procuto Null");
		}
		produtoDao.remover(produto);
	}
	

	
}
