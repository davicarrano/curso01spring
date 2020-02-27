package br.com.davicarrano.curso01.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Categoria;
import br.com.davicarrano.curso01.domains.Produto;
import br.com.davicarrano.curso01.domains.ProdutoDTO;
import br.com.davicarrano.curso01.repositories.CategoriaRepository;
import br.com.davicarrano.curso01.repositories.ProdutoRepository;
import br.com.davicarrano.curso01.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Page<Produto> buscaPorCategorias(String nome, List<Integer> ids, Integer pagina, Integer linhasPorPagina, String direcao, String ordem){
		PageRequest paginaReq = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordem);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome,categorias,paginaReq);
	}

	public Produto buscar(Integer id) {
		Optional<Produto> produtoOptional = produtoRepository.findById(id);
		return produtoOptional.orElseThrow(() -> new ObjectNotFoundException("Produto nao encontrado com id = "+id));
	}

	public List<ProdutoDTO> buscaProdutoPorCategoria(Integer idCategoria) {
		Categoria categoria = new Categoria();
		categoria.setId(idCategoria);
		List<Produto> lista = produtoRepository.findByCategoriasIn(Arrays.asList(categoria));
		List<ProdutoDTO> listaDTO = lista.stream().map(p -> ProdutoDTO.toDTO(p)).collect(Collectors.toList());
		return listaDTO;
	}

}
