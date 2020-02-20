package br.com.davicarrano.curso01.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.davicarrano.curso01.domains.Cidade;
import br.com.davicarrano.curso01.domains.Estado;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

	List<Cidade> findByEstado(Estado estado);

}
