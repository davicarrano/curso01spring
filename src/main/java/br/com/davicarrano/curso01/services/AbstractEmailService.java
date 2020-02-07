package br.com.davicarrano.curso01.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.davicarrano.curso01.domains.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Value("${default.recipient}")
	private String emailTo;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
	protected abstract void sendEmail(SimpleMailMessage sm);
	protected abstract void sendEmail(MimeMessage mm);

	@Override
	public void sendEmailConfirmacaoPedido(Pedido obj) {
		// TODO Auto-generated method stub
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(emailTo);
		sm.setFrom(sender);
		sm.setSubject("\n Pedido confirmado! Codigo: "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("\n" +obj.toString());
		
		return sm;
	}

	@Override
	public void sendEmailHtmlConfirmacaoPedido(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendEmail(mm);			
		}catch(MessagingException e) {
			sendEmailConfirmacaoPedido(obj);
		}
	}
	
	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm,true);
		mmh.setTo(emailTo);
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: "+obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj),true);
		
		return mm;
	}
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context contexto = new Context();
		contexto.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacao_pedido", contexto);
		
	}


}
