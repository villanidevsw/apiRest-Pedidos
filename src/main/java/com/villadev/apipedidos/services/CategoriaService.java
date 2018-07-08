package com.villadev.apipedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.repositories.CategoriaRepository;
import com.villadev.apipedidos.resources.exceptions.IntegridadeDadosException;
import com.villadev.apipedidos.resources.exceptions.RecursoNaoEncontradoException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria buscarPorId(Integer id) {
		Categoria categoria = categoriaRepository.findOne(id);
		if (categoria == null) {
			throw new RecursoNaoEncontradoException("Categoria : " + id + " não disponível");
		}

		return categoria;
	}

	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}

	public Categoria atualizar(Categoria categoria) {
		Categoria novaCategoria = buscarPorId(categoria.getId());
		atualizarDados(novaCategoria, categoria);
		return categoriaRepository.save(novaCategoria);
	}

	private void atualizarDados(Categoria novaCategoria, Categoria categoria) {
		novaCategoria.setNome(categoria.getNome());
	}

	public void deletar(Integer id) {
		buscarPorId(id);
		try {
			categoriaRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadeDadosException("Não é possível excluir uma categoria que possui produtos associados");
		}
	}

	public List<Categoria> buscarTodos() {
		List<Categoria> categorias = categoriaRepository.findAll();
		if (categorias.isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem Categorias disponíveis");
		}

		return categorias;
	}
	
	public Page<Categoria> buscaPaginada(Integer pagina, Integer linhasPorPagina, String ordernarPor, String direcao) {
		PageRequest pageRequest = new PageRequest(pagina, linhasPorPagina, Direction.valueOf(direcao), ordernarPor);
		return categoriaRepository.findAll(pageRequest);
	}
}
