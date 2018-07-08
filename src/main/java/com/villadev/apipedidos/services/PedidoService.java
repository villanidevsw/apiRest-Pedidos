package com.villadev.apipedidos.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.villadev.apipedidos.domain.ItemPedido;
import com.villadev.apipedidos.domain.PagamentoComBoleto;
import com.villadev.apipedidos.domain.Pedido;
import com.villadev.apipedidos.domain.Produto;
import com.villadev.apipedidos.domain.enums.EstadoPagamento;
import com.villadev.apipedidos.repositories.ItemPedidoRepository;
import com.villadev.apipedidos.repositories.PagamentoRepository;
import com.villadev.apipedidos.repositories.PedidoRepository;
import com.villadev.apipedidos.resources.exceptions.RecursoNaoEncontradoException;

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

		return pedido;
	}

	private void inserirItensPedido(Pedido pedido) {
		List<Integer> produtosId = extrairIdsDosPedidos(pedido);
		
		Map<Integer, Double> mapaIdProdutoPreco = buscarIdProdutoPrecoPorIds(produtosId);

		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(mapaIdProdutoPreco.get(ip.getProduto().getId()));
			ip.setPedido(pedido);
		}

		itemPedidoRepository.save(pedido.getItens());
	}

	private Map<Integer, Double> buscarIdProdutoPrecoPorIds(List<Integer> produtosId) {
		
		List<Produto> produtos = produtoService.buscarPorIds(produtosId);
		Map<Integer, Double> mapaPrecoProdutos = produtos.stream().
				collect(Collectors.toMap(p -> p.getId(), p -> p.getPreco()));
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

}
