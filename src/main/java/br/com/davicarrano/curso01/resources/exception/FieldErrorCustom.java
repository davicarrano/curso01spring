package br.com.davicarrano.curso01.resources.exception;

import java.io.Serializable;

public class FieldErrorCustom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String campo;
	
	private String msg;
	
	public FieldErrorCustom() {
		// TODO Auto-generated constructor stub
	}

	public FieldErrorCustom(String campo, String msg) {
		super();
		this.campo = campo;
		this.msg = msg;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
