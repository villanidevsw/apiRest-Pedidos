package com.villadev.apipedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscarPorId(Integer id) {		
		Categoria categoria = categoriaRepository.findOne(id);
		return categoria;
	}
	
	
}
