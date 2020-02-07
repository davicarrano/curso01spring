package br.com.davicarrano.curso01.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.davicarrano.curso01.domains.Produto;
import br.com.davicarrano.curso01.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	
	@GetMapping(value = "/porCategorias")
	public ResponseEntity<Page<Produto>> buscaProdutosPorCategorias(
			@RequestParam(name = "nome", defaultValue = "%") String nome, 
			@RequestParam(name = "categorias") String ids, 
			@RequestParam(name = "p", defaultValue = "0") Integer pagina, 
			@RequestParam(name = "l", defaultValue = "3") Integer linhasPorPagina, 
			@RequestParam(name = "d", defaultValue = "ASC") String direcao, 
			@RequestParam(name = "o", defaultValue = "id") String ordem){
		List<Integer> listaIds = Arrays.asList(ids.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		return ResponseEntity.ok().body(produtoService.buscaPorCategorias(nome, listaIds, pagina, linhasPorPagina, direcao, ordem));
	}
}
