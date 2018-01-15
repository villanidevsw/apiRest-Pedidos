package com.villadev.apipedidos.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecursoExceptionHandler {

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<RespostaJsonErro> recursoNaoEncontrado(RecursoNaoEncontradoException ex,
			HttpServletRequest req) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new RespostaJsonErro(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis()));

	}

}
