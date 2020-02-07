package br.com.davicarrano.curso01.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.davicarrano.curso01.domains.enums.Perfil;



public class ClienteDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Nome do Cliente nao pode ser vazio")
	@Length(min = 5,message = "Nome do cliente deve ter no minimo 5 letras")
	private String nome;

	private String email;
	
	private String cpfOuCnpj;
	
	
	private Integer tipo;
	
	private List<EnderecoDTO> enderecos = new ArrayList<EnderecoDTO>();
	
	private Set<String> telefones = new HashSet<String>();
	
	private Set<Perfil> perfis = new HashSet<>();

	
	public ClienteDTO() {
		// TODO Auto-generated constructor stub
	}



	public ClienteDTO(Integer id, String nome,
			String email, String cpfOuCnpj, Integer tipo, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo;
		this.setPerfis(perfis);
		
	}



	public static ClienteDTO toDTO (Cliente cliente) {
		ClienteDTO clienteDTO = new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail(),cliente.getCpfOuCnpj(),cliente.getTipo().getCod(), cliente.getPerfis());
		for (Endereco end : cliente.getEnderecos()) {
			
			clienteDTO.getEnderecos().add(EnderecoDTO.fromEndereco(end));
		}
		
		
		for (String tel : cliente.getTelefones()) {
			
			clienteDTO.getTelefones().add(tel);
		}
		
		
		return clienteDTO;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}


	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}


	public Integer getTipo() {
		return tipo;
	}


	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}



	public List<EnderecoDTO> getEnderecos() {
		return enderecos;
	}



	public void setEnderecos(List<EnderecoDTO> enderecos) {
		this.enderecos = enderecos;
	}



	public Set<String> getTelefones() {
		return telefones;
	}



	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}



	public Set<Perfil> getPerfis() {
		return perfis;
	}



	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}


	
	
}
