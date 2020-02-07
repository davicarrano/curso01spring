package br.com.davicarrano.curso01.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SMTPEmailService extends AbstractEmailService {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(MockEmailService.class);

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	protected void sendEmail(SimpleMailMessage sm) {
		LOG.info("Simulando envio de email...");
		mailSender.send(sm);
		LOG.info("Email Enviado!");		
		
	}


	@Override
	protected void sendEmail(MimeMessage mm) {
		LOG.info("Simulando envio de email HTML...");
		javaMailSender.send(mm);
		LOG.info("Email Enviado!");		
	}

}
