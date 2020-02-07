package br.com.davicarrano.curso01.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.davicarrano.curso01.domains.Pedido;
import br.com.davicarrano.curso01.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/todos")
	public ResponseEntity<List<Pedido>> buscarTodos(){
		return ResponseEntity.ok().body(pedidoService.buscarTodosPedidos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscar(@PathVariable Integer id){		
		return ResponseEntity.ok().body(pedidoService.buscarPedidoUnico(id));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> inserirPedido(@RequestBody Pedido pedido) {
		pedido = pedidoService.inserir(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
