package com.denner.drogaria.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import com.denner.drogaria.dao.FabricanteDao;
import com.denner.drogaria.model.Fabricante;
import com.denner.drogaria.util.JPAUtil;

@FacesConverter(forClass = Fabricante.class)
public class FabricanteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente,
			String idString) {
		EntityManager manager = JPAUtil.getEntityManager();
		if(idString != null && idString.trim().length() > 0 ){
			Long codigo = Long.valueOf(idString);
			FabricanteDao fabricantes = new FabricanteDao(manager);
			return fabricantes.porId(codigo);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente,
			Object valor) {
		if (valor != null) {
			
			return ((Fabricante) valor).getId().toString();
		}
		return null;
	}

}
