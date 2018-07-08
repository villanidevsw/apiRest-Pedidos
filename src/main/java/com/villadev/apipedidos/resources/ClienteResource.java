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

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.dtos.ClienteDTO;
import com.villadev.apipedidos.domain.dtos.ClienteNovoDTO;
import com.villadev.apipedidos.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
		
		Cliente cliente = clienteService.buscarPorId(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> buscarTodos() {
		List<Cliente> categorias = clienteService.buscarTodos();
		List<ClienteDTO> categoriasDTO = categorias
				.stream()
				.map(c -> new ClienteDTO(c))
				.collect(Collectors.toList());  
		return ResponseEntity.ok().body(categoriasDTO);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNovoDTO clienteNovoDTO) {
		Cliente categoria = clienteService.inserir(Cliente.doDto(clienteNovoDTO));
		URI uri = toURI(categoria,"/{id}");
		
		return ResponseEntity.created(uri).build();
	}

	private URI toURI(Cliente cliente, String path) {
		return ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(path)
				.buildAndExpand(cliente.getId())
				.toUri();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		
		clienteDTO.setId(id);
		clienteService.atualizar(Cliente.doDto(clienteDTO));
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/paginada")
	public ResponseEntity<Page<ClienteDTO>> buscaPaginada(
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina, 
			@RequestParam(value="ordernarPor", defaultValue="nome") String ordernarPor, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		
		Page<Cliente> categorias = clienteService.buscaPaginada(pagina, linhasPorPagina, ordernarPor, direcao);
		Page<ClienteDTO> categoriasDTO = categorias.map(c -> new ClienteDTO(c));  
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	
}
