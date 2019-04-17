package com.denner.drogaria.model;

public enum TipoProduto {
	GENERICO ("Generico"),
	REFERENCIA ("Referencia"),
	SIMILARES ("Similiares");

	private String descricao;

	TipoProduto(String desc) {
		descricao = desc;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	

}
