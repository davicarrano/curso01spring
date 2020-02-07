package br.com.davicarrano.curso01.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import br.com.davicarrano.curso01.services.exception.AuthenticationException;
import br.com.davicarrano.curso01.services.exception.ErroViolacaoIntegridade;
import br.com.davicarrano.curso01.services.exception.FileException;
import br.com.davicarrano.curso01.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest req) {
		StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	@ExceptionHandler(ErroViolacaoIntegridade.class)
	public ResponseEntity<StandardError> violacaoDaIntegridade(ErroViolacaoIntegridade e,
			HttpServletRequest req) {
		StandardError standardError = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> erroValidacao(MethodArgumentNotValidException e,
			HttpServletRequest req) {
		ValidationError erro = new ValidationError(HttpStatus.BAD_REQUEST.value(),
				"Erro de Validacao", System.currentTimeMillis());
		
		
		e.getBindingResult().getFieldErrors().forEach(er -> erro.addErro(new FieldErrorCustom(er.getField(), er.getDefaultMessage())));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<StandardError> erroAutenticacao(AuthenticationException e,
			HttpServletRequest req) {
		StandardError erro = new StandardError(HttpStatus.FORBIDDEN.value(),
				"Erro de Autenticacao", System.currentTimeMillis());
		
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> erroFile(FileException e,
			HttpServletRequest req) {
		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(),
				"Erro do Servidor de Fotos Amazon S3", System.currentTimeMillis());
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e,
			HttpServletRequest req) {
		HttpStatus statusErro = HttpStatus.valueOf(e.getErrorCode());
		StandardError erro = new StandardError(statusErro.value(),
				"Erro do Servidor de Fotos Amazon S3", System.currentTimeMillis());
		
		
		return ResponseEntity.status(statusErro).body(erro);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e,
			HttpServletRequest req) {
		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(),
				"Erro do Servidor de Fotos Amazon S3", System.currentTimeMillis());
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e,
			HttpServletRequest req) {
		StandardError erro = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Erro do Servidor de Fotos Amazon S3", System.currentTimeMillis());
		
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}

}
