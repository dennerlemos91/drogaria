package com.denner.drogaria.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import com.denner.drogaria.dao.ClienteDao;
import com.denner.drogaria.model.Cliente;
import com.denner.drogaria.util.JPAUtil;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente,
			String idString) {
		EntityManager manager = JPAUtil.getEntityManager();
		if(idString != null && idString.trim().length() > 0 ){
			Long codigo = Long.valueOf(idString);
			ClienteDao clienteDao = new ClienteDao(manager);
			return clienteDao.porid(codigo);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente,
			Object valor) {
		if (valor != null) {
			
			return ((Cliente) valor).getId().toString();
		}
		return null;
	}

}
