package br.com.davicarrano.curso01.services;

import br.com.davicarrano.curso01.domains.Pedido;

	
public interface EmailService {

	public void sendEmailConfirmacaoPedido(Pedido obj);
	public void sendEmailHtmlConfirmacaoPedido(Pedido obj);
}
