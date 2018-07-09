package com.villadev.apipedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.villadev.apipedidos.domain.Cidade;
import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.Endereco;
import com.villadev.apipedidos.domain.dtos.ClienteDTO;
import com.villadev.apipedidos.domain.dtos.ClienteNovoDTO;
import com.villadev.apipedidos.repositories.ClienteRepository;
import com.villadev.apipedidos.repositories.EnderecoRepository;
import com.villadev.apipedidos.resources.exceptions.IntegridadeDadosException;
import com.villadev.apipedidos.resources.exceptions.RecursoNaoEncontradoException;
import com.villadev.apipedidos.security.AppUserDetailsService;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	
	public Cliente buscarPorId(Integer id) {	
		
		appUserDetailsService.usuarioTemPermissaoAdmin(id);
		
		Cliente cliente = clienteRepository.findOne(id);
		if (cliente == null) {
			throw new RecursoNaoEncontradoException("Cliente: "+ id +" não existe");
		}
		
		return cliente;
	}
	
	@Transactional
	public Cliente inserir(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.save(cliente.getEnderecos());
		return cliente;
	
	}

	public Cliente atualizar(Cliente cliente) {
		Cliente novoCliente = buscarPorId(cliente.getId());
		atualizarDados(novoCliente, cliente);
		return clienteRepository.save(novoCliente);
	}

	private void atualizarDados(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}

	public void deletar(Integer id) {
		buscarPorId(id);
		try {
			clienteRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadeDadosException("Não é possível excluir um Cliente que possui Pedidos associadas");
		}
	}

	public List<Cliente> buscarTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		if (clientes.isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem Clientes disponíveis");
		}

		return clientes;
	}
	
	public Page<Cliente> buscaPaginada(Integer pagina, Integer linhasPorPagina, String ordernarPor, String direcao) {
		PageRequest pageRequest = new PageRequest(pagina, linhasPorPagina, Direction.valueOf(direcao), ordernarPor);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente doDto(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null,
				null,null);
	}
	
	public Cliente doDto(ClienteNovoDTO clienteNovoDTO) {
		Cliente cliente = new Cliente(null, clienteNovoDTO.getNome(), clienteNovoDTO.getEmail(), clienteNovoDTO.getCpfOuCnpj(),
				clienteNovoDTO.getTipo(),passwordEncoder.encode(clienteNovoDTO.getSenha()));
		Cidade cidade = new Cidade(clienteNovoDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, clienteNovoDTO.getLogradouro(), clienteNovoDTO.getNumero(),
				clienteNovoDTO.getComplemento(), clienteNovoDTO.getBairro(), clienteNovoDTO.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNovoDTO.getTelefone1());
		if (clienteNovoDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNovoDTO.getTelefone2());
		}
		if (clienteNovoDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNovoDTO.getTelefone3());
		}
		return cliente;
	}
	
}
