package com.villadev.apipedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.domain.Produto;
import com.villadev.apipedidos.repositories.CategoriaRepository;
import com.villadev.apipedidos.repositories.ProdutoRepository;
import com.villadev.apipedidos.resources.exceptions.RecursoNaoEncontradoException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto buscarPorId(Integer id) {		
		Produto produto = repo.findOne(id);
		if (produto == null) {
			throw new RecursoNaoEncontradoException("Produto: "+ id +" não existe");
		}
		
		return produto;
	}
	
	public List<Produto> buscarPorIds(List<Integer> produtosId){
		if(produtosId.isEmpty()) {
			throw new RecursoNaoEncontradoException("Produtos não existem");
		}
		return repo.findAll(produtosId);
	}

	public Page<Produto> filtrar(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
