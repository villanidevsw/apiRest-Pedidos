package com.villadev.apipedidos.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA("Pessoa Física"), 
	PESSOA_JURIDICA("Pessoa Jurídica");

	private String tipo;
	
	private TipoCliente(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	
	
	
}
