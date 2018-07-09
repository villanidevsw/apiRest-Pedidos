package com.villadev.apipedidos.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.ItemPedido;
import com.villadev.apipedidos.domain.PagamentoComBoleto;
import com.villadev.apipedidos.domain.Pedido;
import com.villadev.apipedidos.domain.Produto;
import com.villadev.apipedidos.domain.enums.EstadoPagamento;
import com.villadev.apipedidos.repositories.ItemPedidoRepository;
import com.villadev.apipedidos.repositories.PagamentoRepository;
import com.villadev.apipedidos.repositories.PedidoRepository;
import com.villadev.apipedidos.resources.exceptions.AuthorizationException;
import com.villadev.apipedidos.resources.exceptions.RecursoNaoEncontradoException;
import com.villadev.apipedidos.security.AppUserDetails;
import com.villadev.apipedidos.security.AppUserDetailsService;
import com.villadev.apipedidos.services.emails.EmailService;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AppUserDetailsService appUserDetailsService;

	public Pedido buscarPorId(Integer id) {
		Pedido pedido = pedidoRepository.findOne(id);
		if (pedido == null) {
			throw new RecursoNaoEncontradoException("Pedido: " + id + " não existe");
		}

		return pedido;
	}

	@Transactional
	public Pedido inserir(Pedido pedido) {
		pedido = inserirPedido(pedido);
		
		inserirPagamento(pedido);

		inserirItensPedido(pedido);
		
		enviaEmailConfirmacao(pedido);
		
		return pedido;
	}

	private void enviaEmailConfirmacao(Pedido pedido) {
		emailService.sendOrderConfirmationEmail(pedido);		
	}

	private void inserirItensPedido(Pedido pedido) {
		List<Integer> produtosId = extrairIdsDosPedidos(pedido);
		
		Map<Integer, Produto> mapaIdProduto = buscarProdutoPorIds(produtosId);

		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(mapaIdProduto.get(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.save(pedido.getItens());
		
	}

	private Map<Integer, Produto> buscarProdutoPorIds(List<Integer> produtosId) {
		
		List<Produto> produtos = produtoService.buscarPorIds(produtosId);
		Map<Integer, Produto> mapaPrecoProdutos = produtos.stream().
				collect(Collectors.toMap(p -> p.getId(), p -> p));
		return mapaPrecoProdutos;
	}

	private List<Integer> extrairIdsDosPedidos(Pedido pedido) {
		
		List<Integer> produtosId = pedido.getItens().stream()
				.map(it -> it.getProduto().getId())
				.collect(Collectors.toList());
		return produtosId;
	}

	private void inserirPagamento(Pedido pedido) {
		pagamentoRepository.save(pedido.getPagamento());
	}

	private Pedido inserirPedido(Pedido pedido) {
		pedido.setCliente(clienteService.buscarPorId(pedido.getCliente().getId()));
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.ehDoTipoBoleto()) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}

		pedido = pedidoRepository.save(pedido);
		return pedido;
	}
	
	public Page<Pedido> buscaPaginada(Integer pagina, Integer linhasPorPagina, String ordernarPor, String direcao) {
		AppUserDetails usuarioAtual = appUserDetailsService.getCurrentUserAuthenticated();
		if (usuarioAtual == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = new PageRequest(pagina, linhasPorPagina, Direction.valueOf(direcao), ordernarPor);
		Cliente cliente =  clienteService.buscarPorId(usuarioAtual.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
