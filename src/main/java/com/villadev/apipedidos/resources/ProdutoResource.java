package com.villadev.apipedidos.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.villadev.apipedidos.domain.Produto;
import com.villadev.apipedidos.domain.dtos.ProdutoDTO;
import com.villadev.apipedidos.resources.utils.UrlUtils;
import com.villadev.apipedidos.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Integer id) {
		Produto obj = service.buscarPorId(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping("/paginada")
	public ResponseEntity<Page<ProdutoDTO>> buscaPaginada(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina, 
			@RequestParam(value="ordernarPor", defaultValue="nome") String ordernarPor, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		String nomeDecoded = UrlUtils.decodeParam(nome);
		List<Integer> ids = UrlUtils.decodeIntList(categorias);
		Page<Produto> list = service.filtrar(nomeDecoded, ids, pagina, linhasPorPagina, ordernarPor, direcao);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
	
	

}
