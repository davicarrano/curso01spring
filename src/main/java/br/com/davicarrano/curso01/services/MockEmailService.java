package br.com.davicarrano.curso01.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Pedido;


public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	protected void sendEmail(SimpleMailMessage sm) {
		LOG.info("Simulando envio de email...");
		LOG.info(sm.toString());
		LOG.info("Email Enviado!");
		
	}


	@Override
	protected void sendEmail(MimeMessage mm) {
		LOG.info("Simulando envio de email HTML...");
		LOG.info(mm.toString());
		LOG.info("Email Enviado!");
		
	}

}
