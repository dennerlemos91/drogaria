package com.denner.drogaria.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("smartDate")
public class SmartConverter implements Converter {
	
	private static final DateFormat FORMATADOR = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Object getAsObject(FacesContext contexto, UIComponent componente, String valor) {
		Date dataConvertida = null;
		if(valor == null || valor.equals("")){
			return null;
		}
		
		if("hoje".equalsIgnoreCase(valor)){
			dataConvertida = getDataAtual().getTime();
		}else if ("amanha".equalsIgnoreCase(valor) || "amanhã".equalsIgnoreCase(valor)){
			Calendar amanha = getDataAtual();
			amanha.add(Calendar.DAY_OF_MONTH, 1);
			dataConvertida = amanha.getTime();
		}else if ("ontem".equalsIgnoreCase(valor)){
			Calendar ontem = getDataAtual();
			ontem.add(Calendar.DAY_OF_MONTH, -1);
			dataConvertida = ontem.getTime();
		}else{
			try {
				dataConvertida = FORMATADOR.parse(valor);
			} catch (ParseException e) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Data Incorreta.", "Informe uma data correta"));
			}
		}
		return dataConvertida;
	}

	@Override
	public String getAsString(FacesContext contexto, UIComponent componente, Object objeto) {
		return FORMATADOR.format((Date) objeto);
	}
	
	
	private Calendar getDataAtual() {
		Calendar dataAtual = new GregorianCalendar();
		// limpamos informações de hora, minuto, segundo
		// e milissegundos
		dataAtual.set(Calendar.HOUR_OF_DAY, 0);
		dataAtual.set(Calendar.MINUTE, 0);
		dataAtual.set(Calendar.SECOND, 0);
		dataAtual.set(Calendar.MILLISECOND, 0);
		return dataAtual;
		}
	
	
	

}
