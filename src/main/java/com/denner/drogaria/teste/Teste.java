package com.denner.drogaria.teste;

import com.denner.drogaria.dao.LoginDao;
import com.denner.drogaria.model.Usuario;



public class Teste {

	public static void main(String[] args) {
		String user = "asdsd";
		String senha = "asda";
		
		LoginDao loginDao = new LoginDao();
		
		Usuario usuario =  loginDao.login(user, senha);
		
		System.out.println("Autencicado:  "+ usuario.getNomeUsuario());
		
		
	}

}
