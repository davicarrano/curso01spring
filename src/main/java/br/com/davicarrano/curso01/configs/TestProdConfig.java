package br.com.davicarrano.curso01.configs;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.davicarrano.curso01.services.DBService;
import br.com.davicarrano.curso01.services.EmailService;
import br.com.davicarrano.curso01.services.MockEmailService;
import br.com.davicarrano.curso01.services.SMTPEmailService;

@Configuration
@Profile("testprod")
public class TestProdConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		//dbService.instantiate();
		return true;
	}
	
	

	 
	@Bean 
	public EmailService instantiateEmailServer() { 
		return new MockEmailService(); 
	}
	 
}
