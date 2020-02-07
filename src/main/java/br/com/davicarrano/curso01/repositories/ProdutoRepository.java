package br.com.davicarrano.curso01.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.davicarrano.curso01.domains.Categoria;
import br.com.davicarrano.curso01.domains.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

//	@Query(value = "select DISTINCT prod from Produto prod INNER JOIN prod.categorias cat WHERE prod.nome LIKE %:nome% AND cat in (:categorias)")
	//public Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable paginaReq);

	
	public Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable paginaReq);

}
