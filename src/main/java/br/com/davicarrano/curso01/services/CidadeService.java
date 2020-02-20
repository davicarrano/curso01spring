package br.com.davicarrano.curso01.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Cidade;
import br.com.davicarrano.curso01.domains.Estado;
import br.com.davicarrano.curso01.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findCidadesPorEstado(Integer estadoId){
		Estado estado = new Estado(estadoId,"");
		
		return cidadeRepository.findByEstado(estado);
	}
}
