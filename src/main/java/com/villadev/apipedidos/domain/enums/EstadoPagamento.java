package com.villadev.apipedidos.domain.enums;

public enum EstadoPagamento {
	PENDENTE("Pendente"),
	QUITADO("Quitado"),
	CANCELADO("Cancelado");
	
	private String estado;
	
	private EstadoPagamento(String estado) {
		this.estado = estado;
	}
	
	public String getDescricaoEstado() {
		return estado;
	}
	
}
