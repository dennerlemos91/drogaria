package com.denner.drogaria.RN;

import java.io.Serializable;
import java.util.List;

import com.denner.drogaria.Exceptions.NegocioExcepion;
import com.denner.drogaria.dao.FuncionarioDao;
import com.denner.drogaria.model.Funcionario;

@SuppressWarnings("serial")
public class FuncionarioRN implements Serializable {

	private FuncionarioDao funcionarioDao;

	public FuncionarioRN(FuncionarioDao funcionarioDao) {
		this.funcionarioDao = funcionarioDao;
	}

	public void Salvar(Funcionario funcionario) throws NegocioExcepion {
		if (funcionario == null) {
			throw new NegocioExcepion("Fabricante Nulo");
		}
		this.funcionarioDao.guardar(funcionario);
	}

	public List<Funcionario> listar() {
		List<Funcionario> todos = funcionarioDao.todos();
		return todos;
	}

	public void remover(Funcionario funcionario) {
		funcionario = funcionarioDao.porId(funcionario.getId());
		funcionarioDao.remover(funcionario);
	}

}
