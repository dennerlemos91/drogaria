package com.denner.drogaria.model;

public enum FormulaProduto {

	CAPSULA ("Capsula"),
	COMPRIMIDO ("Comprimido"),
	DRAGEAS("Drageas"),
	LIQUIDO("Líquido"),
	POMADA("Pomada");
	
	private String descricao;
	
	FormulaProduto(String desc){
		descricao = desc;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
