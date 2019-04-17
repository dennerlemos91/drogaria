package com.denner.drogaria.model;

public enum CategoriaProduto {

	PERFUMARIA("Perfumaria"), HIGIENE("Higiene"), MEDICAMENTO("Medicamento");

	private String descricao;

	CategoriaProduto(String desc) {
		descricao = desc;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
