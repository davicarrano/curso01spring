package br.com.davicarrano.curso01.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.davicarrano.curso01.domains.Cidade;
import br.com.davicarrano.curso01.services.CidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping(value = "/porEstado/{id}")
	public ResponseEntity<List<Cidade>> buscaCidadadesPorEstado(@PathVariable(name = "id") Integer estadoId){
		List<Cidade> lista = cidadeService.findCidadesPorEstado(estadoId);
		return ResponseEntity.ok().body(lista);
	}

}
