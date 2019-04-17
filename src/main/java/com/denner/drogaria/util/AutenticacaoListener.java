package com.denner.drogaria.util;

import java.io.IOException;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import com.denner.drogaria.bean.LoginBean;
import com.denner.drogaria.model.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();
		boolean isPaginaLogin = paginaAtual.contains("login.xhtml");

		if (!isPaginaLogin) {
			LoginBean loginBean = Faces.getSessionAttribute("loginBean");
			if (loginBean == null) {
				try {
					Faces.redirect("./login.xhtml");
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			Usuario usuario = loginBean.getUsuarioLogado();
			if (usuario == null) {
				try {
					Faces.redirect("./login.xhtml");
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
