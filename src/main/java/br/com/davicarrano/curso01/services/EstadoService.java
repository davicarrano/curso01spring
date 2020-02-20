package br.com.davicarrano.curso01.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Estado;
import br.com.davicarrano.curso01.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findEstados() {
		
		return estadoRepository.findAll() ;
	}

}
