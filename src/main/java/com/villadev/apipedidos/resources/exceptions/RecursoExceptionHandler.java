package com.villadev.apipedidos.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.villadev.apipedidos.json.RespostaJsonFalha;

@ControllerAdvice
public class RecursoExceptionHandler {

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<RespostaJsonFalha> recursoNaoEncontrado(RecursoNaoEncontradoException ex,
			HttpServletRequest req) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new RespostaJsonFalha(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis()));

	}
	
	@ExceptionHandler(IntegridadeDadosException.class)
	public ResponseEntity<RespostaJsonFalha> integridadeDados(IntegridadeDadosException ex,
			HttpServletRequest req) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new RespostaJsonFalha(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis()));

	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RespostaJsonFalha> validacaoDados(MethodArgumentNotValidException ex,
			HttpServletRequest req) {
		
		ValidationError validationError = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), 
				"Erro de validação", ex.getMessage(), req.getRequestURI());
		for (FieldError x : ex.getBindingResult().getFieldErrors()) {
			validationError.addError(x.getField(), x.getDefaultMessage());
		}		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);

	}

}
