package com.villadev.apipedidos.resources.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RecursoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}	
	
	public RecursoNaoEncontradoException(String mensagem, Throwable cause) {
		super(mensagem,cause);
	}
}
