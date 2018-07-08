package com.villadev.apipedidos.json;

import java.io.Serializable;

public class RespostaJsonFalha implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer status;	
	private String mensagem;
	private Long timeStamp;
	private String error;
	private String path;
	
	
	public RespostaJsonFalha(Integer status, String mensagem, Long timeStamp, String error, String path) {
		super();
		this.status = status;
		this.mensagem = mensagem;
		this.timeStamp = timeStamp;
		this.error = error;
		this.path = path;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
