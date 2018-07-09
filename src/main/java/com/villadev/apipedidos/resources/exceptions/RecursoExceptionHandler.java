package com.villadev.apipedidos.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.villadev.apipedidos.json.RespostaJsonFalha;

@ControllerAdvice
public class RecursoExceptionHandler {

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<RespostaJsonFalha> recursoNaoEncontrado(RecursoNaoEncontradoException ex,
			HttpServletRequest req) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaJsonFalha(HttpStatus.NOT_FOUND.value(),
				"Não encontrado", System.currentTimeMillis(), ex.getMessage(), req.getRequestURI()));

	}

	@ExceptionHandler(IntegridadeDadosException.class)
	public ResponseEntity<RespostaJsonFalha> integridadeDados(IntegridadeDadosException ex, HttpServletRequest req) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespostaJsonFalha(HttpStatus.BAD_REQUEST.value(),
				"Integridade de dados", System.currentTimeMillis(), ex.getMessage(), req.getRequestURI()));

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RespostaJsonFalha> validacaoDados(MethodArgumentNotValidException ex,
			HttpServletRequest req) {

		ValidacaoException validacaoErro = new ValidacaoException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Erro de validação", System.currentTimeMillis(), ex.getMessage(), req.getRequestURI());

		ex.getBindingResult().getFieldErrors()
				.forEach(e -> validacaoErro.addError(e.getField(), e.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validacaoErro);

	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<RespostaJsonFalha> semAutorizacao(AuthorizationException ex, HttpServletRequest request) {
		
		ValidacaoException err = new ValidacaoException(HttpStatus.FORBIDDEN.value(), "Acesso negado", System.currentTimeMillis(), 
				ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<ValidacaoException> file(FileException e, HttpServletRequest request) {
		
		ValidacaoException err = new ValidacaoException(HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", 
				System.currentTimeMillis(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<ValidacaoException> amazonService(AmazonServiceException e, HttpServletRequest request) {
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		ValidacaoException err = new ValidacaoException(code.value(), "Erro Amazon Service", System.currentTimeMillis(), 
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<ValidacaoException> amazonClient(AmazonClientException e, HttpServletRequest request) {
		
		ValidacaoException err = new ValidacaoException(HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", System.currentTimeMillis(), 
				e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ValidacaoException> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
		
		ValidacaoException err = new ValidacaoException(HttpStatus.BAD_REQUEST.value(), "Erro S3", 
				System.currentTimeMillis(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
