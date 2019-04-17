package com.denner.drogaria.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_produtos")
public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String descricao;
	private Fabricante fabricante;
	private TipoProduto tipoProduto;
	private CategoriaProduto categoria;
	private FormulaProduto formula;	
	private Integer quantidade;
	private BigDecimal valor;
	private Date dataValidade;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(length = 80, nullable = false)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne
	@JoinColumn(nullable = false)
	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_produto", nullable=false, length = 30)
	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}
	
	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}
	@Enumerated(EnumType.STRING)
	@Column(name = "categotia_produto", nullable = false, length = 40)
	public CategoriaProduto getCategoria() {
		return categoria;
	}
	
	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "formula_produto", nullable = false, length = 40)
	public FormulaProduto getFormula() {
		return formula;
	}
	
	public void setFormula(FormulaProduto formula) {
		this.formula = formula;
	}
	
	@Column(nullable = false, length = 10)
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(nullable = false,precision = 6, scale = 2)
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_validade", nullable = false, length = 10)
	public Date getDataValidade() {
		return dataValidade;
	}
	
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
