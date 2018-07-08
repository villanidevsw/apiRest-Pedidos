package com.villadev.apipedidos.resources.exceptions;

import java.io.Serializable;

public class CampoMensagemException implements Serializable {
	private static final long serialVersionUID = 1L;

	private String campo;
	private String mensagem;

	public CampoMensagemException() {
	}

	public CampoMensagemException(String campo, String mensagem) {
		super();
		this.setCampo(campo);
		this.setMensagem(mensagem);
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
}
