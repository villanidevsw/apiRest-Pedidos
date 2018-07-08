package com.villadev.apipedidos;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.domain.Cidade;
import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.Endereco;
import com.villadev.apipedidos.domain.Estado;
import com.villadev.apipedidos.domain.ItemPedido;
import com.villadev.apipedidos.domain.Pagamento;
import com.villadev.apipedidos.domain.PagamentoComBoleto;
import com.villadev.apipedidos.domain.PagamentoComCartao;
import com.villadev.apipedidos.domain.Pedido;
import com.villadev.apipedidos.domain.Produto;
import com.villadev.apipedidos.domain.enums.EstadoPagamento;
import com.villadev.apipedidos.domain.enums.TipoCliente;
import com.villadev.apipedidos.repositories.CategoriaRepository;
import com.villadev.apipedidos.repositories.CidadeRepository;
import com.villadev.apipedidos.repositories.ClienteRepository;
import com.villadev.apipedidos.repositories.EnderecoRepository;
import com.villadev.apipedidos.repositories.EstadoRepository;
import com.villadev.apipedidos.repositories.ItemPedidoRepository;
import com.villadev.apipedidos.repositories.PagamentoRepository;
import com.villadev.apipedidos.repositories.PedidoRepository;
import com.villadev.apipedidos.repositories.ProdutoRepository;

@SpringBootApplication
public class Aplicacao implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Aplicacao.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// cadastro categoria
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		// cadastro produto
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 500.00);
		Produto p3 = new Produto(null, "Mouse", 20.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		categoriaRepository.save(Arrays.asList(cat1, cat2,cat3,cat4, cat5,cat6, cat7));
		produtoRepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		// cadastro estado
		Estado mg = new Estado(null, "Minas Gerais");
		Estado sp = new Estado(null, "São Paulo");
		// cadastro cidade
		Cidade uberlandia = new Cidade(null, "Uberlandia", mg);
		Cidade saoPaulo = new Cidade(null, "São Paulo", sp);
		Cidade campinas = new Cidade(null, "Campinas", sp);

		mg.getCidades().addAll(Arrays.asList(uberlandia));
		sp.getCidades().addAll(Arrays.asList(saoPaulo, campinas));

		estadoRepository.save(Arrays.asList(mg, sp));
		cidadeRepository.save(Arrays.asList(uberlandia, saoPaulo, campinas));

		// cadastro cliente
		Cliente joao = new Cliente(null, "Joao da Silva", "joao@email.com", "11111111111", TipoCliente.PESSOA_FISICA);
		joao.getTelefones().addAll(Arrays.asList("9999-9999", "7777-7777"));

		Endereco enderecoJoao = new Endereco(null, "Rua do teste", "25", "casa", "bairro teste", "8888-8888", joao,
				uberlandia);
		Endereco enderecoJoao2 = new Endereco(null, "Rua do teste2", "252", "casa", "bairro teste2", "9999-9999", joao,
				campinas);

		joao.getEnderecos().addAll(Arrays.asList(enderecoJoao, enderecoJoao2));

		Cliente pedroPinturasSA = new Cliente(null, "Pinturas SA", "pinturassa@email.com", "33333333333",
				TipoCliente.PESSOA_JURIDICA);
		pedroPinturasSA.getTelefones().addAll(Arrays.asList("8888-8888"));

		Endereco enderecoPedroPinturasSA = new Endereco(null, "Rua do teste3", "235", "Barracao", "bairro teste3",
				"8888-8888", pedroPinturasSA, saoPaulo);
		pedroPinturasSA.getEnderecos().addAll(Arrays.asList(enderecoPedroPinturasSA));

		clienteRepository.save(Arrays.asList(joao, pedroPinturasSA));
		enderecoRepository.save(Arrays.asList(enderecoJoao, enderecoJoao2, enderecoPedroPinturasSA));

		// cria um pedido
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		Pedido pedidoJoao = new Pedido(null, formatter.parse("30/09/2017 10:32:28"), enderecoJoao, joao);

		Pedido pedidoJoao2 = new Pedido(null, formatter.parse("01/10/2017 17:05:09"), enderecoJoao2, joao);

		// cria o pagamento do pedido
		Pagamento pgtoCartao = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedidoJoao, 3);
		pedidoJoao.setPagamento(pgtoCartao);

		Pagamento pgtoBoleto = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedidoJoao2,
				formatter.parse("02/10/2017 23:59:59"), formatter.parse("01/10/2017 12:15:32"),
				"89800107010137867161201255520");

		pedidoJoao2.setPagamento(pgtoBoleto);

		joao.getPedidos().addAll(Arrays.asList(pedidoJoao, pedidoJoao2));

		pedidoRepository.save(Arrays.asList(pedidoJoao, pedidoJoao2));
		pagamentoRepository.save(Arrays.asList(pgtoBoleto, pgtoCartao));

		// cria os itens dos pedidos
		ItemPedido itemPedidoJoao = new ItemPedido(pedidoJoao, p3, 0.00, 1, p3.getPreco());
		ItemPedido itemPedidoJoao2 = new ItemPedido(pedidoJoao, p2, 20.00, 1, (p2.getPreco() - 20.00));
		ItemPedido itemPedidoJoao3 = new ItemPedido(pedidoJoao2, p1, 0.00, 1, p1.getPreco());

		pedidoJoao.getItens().addAll(Arrays.asList(itemPedidoJoao, itemPedidoJoao2));
		pedidoJoao2.getItens().addAll(Arrays.asList(itemPedidoJoao3));

		p3.getItens().addAll(Arrays.asList(itemPedidoJoao));
		p2.getItens().addAll(Arrays.asList(itemPedidoJoao2));
		p1.getItens().addAll(Arrays.asList(itemPedidoJoao3));

		itemPedidoRepository.save(Arrays.asList(itemPedidoJoao, itemPedidoJoao2, itemPedidoJoao3));
	}
}
