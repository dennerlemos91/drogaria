package com.denner.drogaria.teste;

import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class CalculadoraBean {

	private Double valorA;
	private Double valorB;
	private Double resultado;

	private Date dataBase;
	private Integer dias;
	private Date resultados;

	// Getes e Settes calculadora
	public Double getValorA() {
		return valorA;
	}

	public void setValorA(Double valorA) {
		this.valorA = valorA;
	}

	public Double getValorB() {
		return valorB;
	}

	public void setValorB(Double valorB) {
		this.valorB = valorB;
	}

	public Double getResultado() {
		return resultado;
	}

	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}

	// Getes e Settes Data

	public Date getDataBase() {
		return dataBase;
	}

	public void setDataBase(Date dataBase) {
		this.dataBase = dataBase;
	}

	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias = dias;
	}

	public Date getResultados() {
		return resultados;
	}

	public void setResultados(Date resultados) {
		this.resultados = resultados;
	}

	public void adicionar(){
		Calendar dataCalculo = Calendar.getInstance();
		dataCalculo.setTime(this.dataBase);
		dataCalculo.add(Calendar.DAY_OF_MONTH, dias);
		this.resultados = dataCalculo.getTime();
	}

	public void somar() {
		this.resultado = this.valorA + this.valorB;
	}

}
