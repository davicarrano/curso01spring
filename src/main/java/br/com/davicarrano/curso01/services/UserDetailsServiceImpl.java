package br.com.davicarrano.curso01.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Cliente;
import br.com.davicarrano.curso01.repositories.ClienteRepository;
import br.com.davicarrano.curso01.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Cliente> cli = clienteRepository.findByEmail(email);
		if (!cli.isPresent()) {
			throw new UsernameNotFoundException(email); 
		}
		
		return new UserSS(cli.get().getId(),cli.get().getEmail(), cli.get().getSenha(), cli.get().getPerfis());
	}

}
