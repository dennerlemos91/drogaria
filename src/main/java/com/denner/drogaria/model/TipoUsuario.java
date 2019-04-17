package com.denner.drogaria.model;


public enum TipoUsuario {

	ADMINISTRADOR("Administrador"), COMUM("Comum");

	private String descricao;

	TipoUsuario(String desc) {
		descricao = desc;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
