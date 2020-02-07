package br.com.davicarrano.curso01.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.davicarrano.curso01.security.JWTUtil;
import br.com.davicarrano.curso01.security.UserSS;
import br.com.davicarrano.curso01.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping(value = "refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse resp){
		UserSS user = UserService.authenticated();
		String novoToken = jwtUtil.generateToken(user.getUsername());
		resp.addHeader("Authorization", "Bearer "+novoToken);
		resp.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

}
