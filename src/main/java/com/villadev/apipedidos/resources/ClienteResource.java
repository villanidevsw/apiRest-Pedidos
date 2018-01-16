package com.villadev.apipedidos.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.json.RespostaJsonSucesso;
import com.villadev.apipedidos.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/{id}")
	public RespostaJsonSucesso buscarPorId(@PathVariable Integer id) {
		
		RespostaJsonSucesso sucesso = new RespostaJsonSucesso();
				
		Cliente cliente = clienteService.buscarPorId(id);
		
		sucesso.addObjeto(cliente);
		
		return sucesso;
	}
	
	
}
