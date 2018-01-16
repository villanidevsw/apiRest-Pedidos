package com.villadev.apipedidos.json;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;

public class RespostaJsonSucesso  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private Set<Object> dados = new HashSet<>();
	
	public RespostaJsonSucesso() {
		this.status = HttpStatus.OK.value();
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void addObjeto(Object obj) {
		this.dados.add(obj);
	}

	public Set<Object> getDados() {
		return dados;
	}

}
