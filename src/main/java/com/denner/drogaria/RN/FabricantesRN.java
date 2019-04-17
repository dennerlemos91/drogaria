package com.denner.drogaria.RN;

import java.util.List;

import com.denner.drogaria.Exceptions.NegocioExcepion;
import com.denner.drogaria.dao.FabricanteDao;
import com.denner.drogaria.model.Fabricante;

public class FabricantesRN {

	private FabricanteDao fabricanteDao;
	
	public FabricantesRN(FabricanteDao fabricanteDao){
		this.fabricanteDao = fabricanteDao;
	}

	
	public void Salvar(Fabricante fabricante) throws NegocioExcepion{
		if(fabricante == null){
			throw new NegocioExcepion("Fabricante Nulo");
		}
		this.fabricanteDao.guardar(fabricante);
	}
	
	public List<Fabricante> listar(){
		List<Fabricante> todos = fabricanteDao.todos();
		return todos;
	}

	public void remover(Fabricante fabricante){
		fabricante = fabricanteDao.porId(fabricante.getId());
		fabricanteDao.remover(fabricante);
	}
	
	
	
}
