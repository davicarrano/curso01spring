package br.com.davicarrano.curso01.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Categoria;
import br.com.davicarrano.curso01.domains.CategoriaDTO;
import br.com.davicarrano.curso01.repositories.CategoriaRepository;
import br.com.davicarrano.curso01.services.exception.ErroViolacaoIntegridade;
import br.com.davicarrano.curso01.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	

	public Categoria buscar(Integer id) {

		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

		return categoriaOptional.orElseThrow(() -> new ObjectNotFoundException("Categoria de id " + id + " nao encontrado! Tipo " + Categoria.class.getCanonicalName()));
	}

	public List<CategoriaDTO> buscarTodas() {
		List<Categoria> lista = categoriaRepository.findAll();
		List<CategoriaDTO> listaDTO = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

		if (listaDTO.size() == 0) {
			throw new ObjectNotFoundException("Não há categorias cadastradas! Tipo " + Categoria.class.getCanonicalName());
		}
		return listaDTO;
	}

	public Categoria salvar(Categoria obj) {
		obj.setId(null);
		Categoria c = categoriaRepository.save(obj);		
		return c;
	}
	
	public Categoria atualizar (Categoria obj) {
		buscar(obj.getId());
		Categoria c = categoriaRepository.save(obj);		
		return c;
		
	}

	public void deletar(Integer id) {
		try {
			Categoria catDelet = buscar(id);
			categoriaRepository.delete(catDelet);
			
		}catch(DataIntegrityViolationException e) {
			throw new ErroViolacaoIntegridade("Nao e possivel excluir uma categoria que tenha produtos!");
		}
		
	}
	
	public Page<CategoriaDTO> bucarPagina(Integer pagina, Integer linhasPorPagina, String ordem, String direcao){
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, direcao.equals("asc") ?  Direction.ASC : Direction.DESC, ordem);
		Page<Categoria> page = categoriaRepository.findAll(pageRequest);
		Page<CategoriaDTO> pageDTO = page.map(obj -> new CategoriaDTO(obj));
		return pageDTO;
		
	}
	
	public Categoria fromDTO(CategoriaDTO obj) {
		return new Categoria(obj.getId(),obj.getNome());
	}
}
