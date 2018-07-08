package com.villadev.apipedidos.resources.exceptions;

import java.io.Serializable;

public class CampoMensagemException implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;

	public CampoMensagemException() {
	}

	public CampoMensagemException(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
