package br.com.davicarrano.curso01.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.davicarrano.curso01.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
		}catch(Exception e) {
			return null;
		}
	}
}
