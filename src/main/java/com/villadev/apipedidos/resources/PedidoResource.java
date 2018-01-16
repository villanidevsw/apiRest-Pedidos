package com.villadev.apipedidos.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villadev.apipedidos.domain.Pedido;
import com.villadev.apipedidos.json.RespostaJsonSucesso;
import com.villadev.apipedidos.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService PedidoService;
	
	@GetMapping("/{id}")
	public RespostaJsonSucesso buscarPorId(@PathVariable Integer id) {
		
		RespostaJsonSucesso sucesso = new RespostaJsonSucesso();
		
		Pedido pedido = PedidoService.buscarPorId(id);
		
		sucesso.addObjeto(pedido);
		
		return sucesso;
	}
	
	
}
