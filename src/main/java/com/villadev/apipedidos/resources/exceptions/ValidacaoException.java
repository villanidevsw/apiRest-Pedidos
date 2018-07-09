package com.villadev.apipedidos.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.villadev.apipedidos.json.RespostaJsonFalha;

public class ValidacaoException extends RespostaJsonFalha {
	private static final long serialVersionUID = 1L;
	
	private List<CampoMensagemException> errors = new ArrayList<>();
	
	public ValidacaoException(Integer status, String mensagem, Long timeStamp, String error, String path) {
		super(status, mensagem, timeStamp, error, path);
	}

	public List<CampoMensagemException> getErros() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new CampoMensagemException(fieldName, messagem));
	}

}
