package com.villadev.apipedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.repositories.ClienteRepository;
import com.villadev.apipedidos.resources.exceptions.RecursoNaoEncontradoException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository ClienteRepository;
	
	public Cliente buscarPorId(Integer id) {		
		Cliente cliente = ClienteRepository.findOne(id);
		if (cliente == null) {
			throw new RecursoNaoEncontradoException("Cliente: "+ id +" não existe");
		}
		
		return cliente;
	}
	
	
}
