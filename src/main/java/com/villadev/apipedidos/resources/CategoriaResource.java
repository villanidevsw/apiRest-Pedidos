package com.villadev.apipedidos.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.domain.dtos.CategoriaDTO;
import com.villadev.apipedidos.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) {
		
		Categoria categoria = categoriaService.buscarPorId(id);
				
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> buscarTodos() {
		List<Categoria> categorias = categoriaService.buscarTodos();
		List<CategoriaDTO> categoriasDTO = categorias
				.stream()
				.map(c -> new CategoriaDTO(c))
				.collect(Collectors.toList());  
		return ResponseEntity.ok().body(categoriasDTO);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.inserir(Categoria.doDto(categoriaDTO));
		URI uri = toURI(categoria,"/{id}");
		
		return ResponseEntity.created(uri).build();
	}

	private URI toURI(Categoria categoria, String path) {
		return ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(path)
				.buildAndExpand(categoria.getId())
				.toUri();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
		
		categoriaDTO.setId(id);
		categoriaService.atualizar(Categoria.doDto(categoriaDTO));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoriaService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/paginada")
	public ResponseEntity<Page<CategoriaDTO>> buscaPaginada(
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina, 
			@RequestParam(value="ordernarPor", defaultValue="nome") String ordernarPor, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		
		Page<Categoria> categorias = categoriaService.buscaPaginada(pagina, linhasPorPagina, ordernarPor, direcao);
		Page<CategoriaDTO> categoriasDTO = categorias.map(c -> new CategoriaDTO(c));  
		return ResponseEntity.ok().body(categoriasDTO);
	}
}
