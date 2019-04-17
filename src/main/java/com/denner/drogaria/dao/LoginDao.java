package com.denner.drogaria.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.denner.drogaria.model.Usuario;
import com.denner.drogaria.util.JPAUtil;

public class LoginDao implements Serializable {

	private static final long serialVersionUID = 1L;

	EntityManager manager = JPAUtil.getEntityManager();

	public Usuario login(String usuario, String senha) {
		Session sessao = manager.unwrap(Session.class);
		
		try{
			
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.add(Restrictions.eq("nomeUsuario", usuario));
			consulta.add(Restrictions.eq("senha", senha));
			
			Usuario resultado = (Usuario) consulta.uniqueResult();
			
			return resultado;
		}catch(RuntimeException erro){
			throw erro;
		}finally{
			sessao.close();
		}
		
	}

}
