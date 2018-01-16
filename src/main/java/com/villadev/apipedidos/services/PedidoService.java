package com.villadev.apipedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.villadev.apipedidos.domain.Pedido;
import com.villadev.apipedidos.repositories.PedidoRepository;
import com.villadev.apipedidos.resources.exceptions.RecursoNaoEncontradoException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarPorId(Integer id) {
		Pedido pedido = pedidoRepository.findOne(id);
		if (pedido == null) {
			throw new RecursoNaoEncontradoException("Pedido: "+ id +" não existe");
		}
		
		return pedido;
	}

}
