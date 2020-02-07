package br.com.davicarrano.curso01.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.davicarrano.curso01.domains.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Integer>{
	public Cliente findByEmail(String email);
}
