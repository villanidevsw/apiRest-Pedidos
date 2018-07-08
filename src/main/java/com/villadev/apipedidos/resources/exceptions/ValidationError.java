package com.villadev.apipedidos.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.villadev.apipedidos.json.RespostaJsonFalha;

public class ValidationError extends RespostaJsonFalha {
	private static final long serialVersionUID = 1L;
	
	public ValidationError(Integer status, String mensagem, Long timeStamp, String error, String path) {
		super(status, mensagem, timeStamp, error, path);
	}


	private List<FieldMessageException> erros = new ArrayList<>();

	
	public List<FieldMessageException> getErros() {
		return erros;
	}

	public void addError(String fieldName, String messagem) {
		erros.add(new FieldMessageException(fieldName, messagem));
	}

}
