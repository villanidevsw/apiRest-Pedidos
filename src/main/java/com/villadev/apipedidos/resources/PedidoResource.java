package com.villadev.apipedidos.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.villadev.apipedidos.domain.Pedido;
import com.villadev.apipedidos.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {
		Pedido pedido = pedidoService.buscarPorId(id);	
		
		return ResponseEntity.ok().body(pedido);
	}
	
	@PostMapping()
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = pedidoService.inserir(obj);
		
		URI uri = toURI(obj, "/{id}");
		
		return ResponseEntity.created(uri).build();
	}

	private URI toURI(Pedido obj, String path) {
		return ServletUriComponentsBuilder.fromCurrentRequest()
				.path(path)
				.buildAndExpand(obj.getId())
				.toUri();
	}
	
	@GetMapping("/paginada")
	public ResponseEntity<Page<Pedido>> buscaPaginada(
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina, 
			@RequestParam(value="ordernarPor", defaultValue="instante") String ordernarPor, 
			@RequestParam(value="direcao", defaultValue="DESC") String direcao) {
		
		Page<Pedido> pedidos = pedidoService.buscaPaginada(pagina, linhasPorPagina, ordernarPor, direcao);
		return ResponseEntity.ok().body(pedidos);
	}
	
}
