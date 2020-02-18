package br.com.davicarrano.curso01.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.davicarrano.curso01.domains.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Integer>{
	public Optional<Cliente> findByEmail(String email);
}
