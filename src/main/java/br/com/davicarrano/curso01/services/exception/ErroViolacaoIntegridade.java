package br.com.davicarrano.curso01.services.exception;

public class ErroViolacaoIntegridade extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	public ErroViolacaoIntegridade(String msg) {
		super(msg);
	}
	public ErroViolacaoIntegridade(String msg, Throwable cause) {
		super (msg,cause);
		
		
	}
}
