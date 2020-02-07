package br.com.davicarrano.curso01.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.davicarrano.curso01.domains.Cliente;
import br.com.davicarrano.curso01.domains.ClienteDTO;
import br.com.davicarrano.curso01.domains.ClienteNewDTO;
import br.com.davicarrano.curso01.services.ClienteService;
import br.com.davicarrano.curso01.services.S3Service;

@RequestMapping(value = "/clientes")
@RestController
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired	
	private S3Service s3service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> bucarCliente(@PathVariable Integer id) {

		Cliente cliente = clienteService.buscar(id);
		ClienteDTO clienteDTO = ClienteDTO.toDTO(cliente);

		return ResponseEntity.ok().body(clienteDTO);
	}

	@GetMapping(value = "/todos")
	public ResponseEntity<List<ClienteDTO>> buscarTodosClientes() {
		List<Cliente> lista = clienteService.buscarTodos();
		
		List<ClienteDTO> listaDTO = lista.stream().map(c -> ClienteDTO.toDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizarCliente(@RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){
		Cliente cliente = clienteService.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente = clienteService.atualizar(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> inserirCliente(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
		Cliente cliente = clienteService.fromNewDTO(clienteNewDTO); 
		cliente = clienteService.salvar(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").build(cliente.getId());
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Integer id){
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/pagina")
	public ResponseEntity<Page<ClienteDTO>> buscarPagina(
			@RequestParam(name="p", defaultValue = "0") Integer pagina,
			@RequestParam(name="l", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(name="o", defaultValue = "id") String ordem,
			@RequestParam(name="d", defaultValue = "ASC") String direcao
			){
		Page<Cliente> paginaRetorno = clienteService.buscarPaginacao(pagina,linhasPorPagina,ordem,direcao);
		Page<ClienteDTO> paginaDTO = paginaRetorno.map(c -> ClienteDTO.toDTO(c));
		return ResponseEntity.ok().body(paginaDTO);
	}
	
	@PostMapping(value = "/foto")
	public ResponseEntity<Void> uploadFoto(@RequestParam(name = "file") MultipartFile multipartFile){
		URI uri = s3service.uploadFile(multipartFile);
		return ResponseEntity.created(uri).build();
	}
}
