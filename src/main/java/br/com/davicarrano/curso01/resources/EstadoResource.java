package br.com.davicarrano.curso01.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.davicarrano.curso01.domains.Estado;
import br.com.davicarrano.curso01.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	
	@GetMapping(value = "todos")
	public ResponseEntity<List<Estado>> bucarEstados(){
		
		List<Estado> lista = estadoService.findEstados(); 
		return ResponseEntity.ok().body(lista);
	}
}
