package com.villadev.apipedidos.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods	
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome,List<Categoria> categorias,
			Pageable pageRequest);
	
	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% "
			+ "AND cat IN :categorias")
	Page<Produto> filtrar(@Param("nome") String nome,
			@Param("categorias") List<Categoria> categorias, Pageable pageRequest);

}
