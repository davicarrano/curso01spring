package br.com.davicarrano.curso01.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.davicarrano.curso01.domains.Categoria;
import br.com.davicarrano.curso01.domains.CategoriaDTO;
import br.com.davicarrano.curso01.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	
	@GetMapping(value="/todas")
	public ResponseEntity<List<CategoriaDTO>> listarCategorias() {

		return ResponseEntity.ok().body(categoriaService.buscarTodas());
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscarCategoria(@PathVariable Integer id){		
		Categoria categoria = categoriaService.buscar(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> inserirCategoria (@RequestBody @Valid CategoriaDTO obj){
		Categoria cat = categoriaService.fromDTO(obj);
		cat = categoriaService.salvar(cat);
		
		
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cat.getId()).toUri();
		return ResponseEntity.created(uri).build(); 
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizarCategoria(@Valid @RequestBody CategoriaDTO obj, @PathVariable Integer id){
		Categoria cat = categoriaService.fromDTO(obj);
		cat.setId(id);
		cat = categoriaService.atualizar(cat);
		return ResponseEntity.noContent().build(); 

	}
	
	
	@DeleteMapping(value = "/{id}") 
	public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id){ 
		categoriaService.deletar(id); 
		return ResponseEntity.noContent().build();
	}
	 

	@GetMapping(value="/page")
	public ResponseEntity<Page<CategoriaDTO>> buscarCategoriasPorPagina(
			@RequestParam(name = "page", defaultValue = "0") Integer pagina, 
			@RequestParam(name = "linhas", defaultValue = "3") Integer linhasPorPagina, 
			@RequestParam(name = "ordem", defaultValue = "nome") String ordem, 
			@RequestParam(name = "dir", defaultValue = "asc") String direcao){
		
		return ResponseEntity.ok().body(categoriaService.bucarPagina(pagina, linhasPorPagina, ordem, direcao));
	}

}
