package com.denner.drogaria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.denner.drogaria.model.ItemVenda;
import com.denner.drogaria.model.Produto;
import com.denner.drogaria.model.Venda;
import com.denner.drogaria.util.JPAUtil;

public class VendaDao {
	EntityManager manager = JPAUtil.getEntityManager();
	EntityTransaction trx = manager.getTransaction();

	public void adicionar(Venda venda, List<ItemVenda> itemVendas) {
		try {
			trx.begin();
			manager.persist(venda);

			for (int i = 0; i < itemVendas.size(); i++) {
				//salvar cada item da venda
				ItemVenda itemVenda = itemVendas.get(i);
				itemVenda.setVenda(venda);
				manager.persist(itemVenda);
				
				//Baixa no estoque
				Produto produto = itemVenda.getProduto();
				int qtd = produto.getQuantidade() - itemVenda.getQuantidade();
				if(qtd >= 0 ){

					produto.setQuantidade(qtd);
					
					manager.merge(produto);
				}else{
					throw new RuntimeException("Quantidade Insuficiente no estoque.");
				}
			}

			trx.commit();
		} catch (RuntimeException erro) {
			if (trx != null) {
				trx.rollback();
			}
			throw erro;
		}
	}

}
