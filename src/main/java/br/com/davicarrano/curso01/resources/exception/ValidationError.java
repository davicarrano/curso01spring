package br.com.davicarrano.curso01.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}


	private List<FieldErrorCustom> erros = new ArrayList<FieldErrorCustom>();
	
	public List<FieldErrorCustom> getErros (){
		return erros;
	}
	
	public void addErro(FieldErrorCustom fieldError) {
		erros.add(fieldError);
	}
}
