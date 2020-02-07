package br.com.davicarrano.curso01.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.davicarrano.curso01.domains.Cidade;
import br.com.davicarrano.curso01.domains.Cliente;
import br.com.davicarrano.curso01.domains.ClienteDTO;
import br.com.davicarrano.curso01.domains.ClienteNewDTO;
import br.com.davicarrano.curso01.domains.Endereco;
import br.com.davicarrano.curso01.domains.enums.Perfil;
import br.com.davicarrano.curso01.domains.enums.TipoCliente;
import br.com.davicarrano.curso01.repositories.ClienteRepository;
import br.com.davicarrano.curso01.repositories.EnderecoRepository;
import br.com.davicarrano.curso01.security.UserSS;
import br.com.davicarrano.curso01.services.exception.AuthenticationException;
import br.com.davicarrano.curso01.services.exception.ErroViolacaoIntegridade;
import br.com.davicarrano.curso01.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente buscar (Integer id) {
		UserSS usuarioLogado = UserService.authenticated();	
		if (usuarioLogado==null || !usuarioLogado.hasHole(Perfil.ADMIN) && !id.equals(usuarioLogado.getId())) {
			throw new AuthenticationException("Acesso negado");
		}
		
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		return clienteOptional.orElseThrow(() -> new ObjectNotFoundException("Cliente nao localizado com id: "+id)) ;
	}

	public List<Cliente> buscarTodos() {
		List<Cliente> lista = clienteRepository.findAll();
		if (lista.size() == 0 ) {
			throw new ObjectNotFoundException("Lista de Clientes nao existe!");
		}
		return lista;
	}
	
	public Cliente atualizar(Cliente cliente) {
		Cliente clientePersistido = buscar(cliente.getId());
		cliente = sincronizarDadosClientes(cliente,clientePersistido);
		cliente = clienteRepository.save(cliente);
		return cliente;
	}
	
	private Cliente sincronizarDadosClientes(Cliente cliente, Cliente clientePersistido) {
		clientePersistido.setNome(cliente.getNome());
		clientePersistido.setEmail(cliente.getEmail());
		return clientePersistido;
	}

	@Transactional
	public Cliente salvar(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		
		enderecoRepository.saveAll(cliente.getEnderecos());
		
		return cliente;
	}
	
	public void deletar (Integer id) {
		try {
			Cliente clienteDeletar = buscar(id);
			clienteRepository.delete(clienteDeletar);
			
		}catch(DataIntegrityViolationException e) {
			throw new ErroViolacaoIntegridade("Nao e possivel excluir um cliente que tenha Enderecos lancados!");
		}
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		cliente.setNome(clienteDTO.getNome());
		cliente.setEmail(clienteDTO.getEmail());
		return  cliente;
	}

	public Page<Cliente> buscarPaginacao(Integer pagina, Integer linhasPorPagina, String ordem, String direcao) {
		PageRequest pag = PageRequest.of(pagina, linhasPorPagina, direcao.equals("ASC") ? Direction.ASC : Direction.DESC, ordem);
		return clienteRepository.findAll(pag);
	}

	public Cliente fromNewDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente();
		cliente.setNome(clienteNewDTO.getNome());
		cliente.setCpfOuCnpj(clienteNewDTO.getCpfOuCnpj());
		cliente.setEmail(clienteNewDTO.getEmail());
		
		if (clienteNewDTO.getTipo() != null)
			cliente.setTipo(TipoCliente.toEnum(clienteNewDTO.getTipo()));;
		
		cliente.getEnderecos().add(new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, new Cidade(clienteNewDTO.getCidadeId(),null,null)));
		if (clienteNewDTO.getTelefone1() != null)
			cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		if (clienteNewDTO.getTelefone2() != null)
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		if (clienteNewDTO.getTelefone3() != null)
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		return  cliente;
	}
	
}
