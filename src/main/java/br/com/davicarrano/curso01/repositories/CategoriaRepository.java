package br.com.davicarrano.curso01.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.davicarrano.curso01.domains.Categoria;

@Repository
public interface CategoriaRepository extends  JpaRepository<Categoria,Integer> {

}
