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
		//cadastro categoria
		Categoria categoriaInformatica = new Categoria(null,"Informática");
		Categoria categoriaEscritorio = new Categoria(null,"Escritório");
		//cadastro produto
		Produto computador = new Produto(null,"Computador",2000.00);
		Produto impressora = new Produto(null,"Impressora",500.00);
		Produto mouse = new Produto(null,"Mouse",20.00);
		
		categoriaInformatica.getProdutos().addAll(Arrays.asList(computador,impressora,mouse));
		categoriaEscritorio.getProdutos().addAll(Arrays.asList(impressora));
		
		computador.getCategorias().addAll(Arrays.asList(categoriaInformatica));
		impressora.getCategorias().addAll(Arrays.asList(categoriaInformatica,categoriaEscritorio));
		mouse.getCategorias().addAll(Arrays.asList(categoriaInformatica));		
		
		categoriaRepository.save(Arrays.asList(categoriaInformatica,categoriaEscritorio));		
		produtoRepository.save(Arrays.asList(computador,impressora,mouse));
		
		//cadastro estado
		Estado mg = new Estado(null, "Minas Gerais");
		Estado sp = new Estado(null, "São Paulo");
		//cadastro cidade
		Cidade uberlandia = new Cidade(null, "Uberlandia", mg);
		Cidade saoPaulo = new Cidade(null, "São Paulo", sp);
		Cidade campinas = new Cidade(null, "Campinas", sp);
		
		mg.getCidades().addAll(Arrays.asList(uberlandia));
		sp.getCidades().addAll(Arrays.asList(saoPaulo,campinas));
		
		estadoRepository.save(Arrays.asList(mg,sp));
		cidadeRepository.save(Arrays.asList(uberlandia,saoPaulo,campinas));
		
		//cadastro cliente
		Cliente joao = new Cliente(null, "Joao da Silva", "joao@email.com", "11111111111",TipoCliente.PESSOA_FISICA);
		joao.getTelefones().addAll(Arrays.asList("9999-9999","7777-7777"));
		
		Endereco enderecoJoao = new Endereco(null, "Rua do teste", "25", "casa", "bairro teste", "8888-8888", joao, uberlandia);
		Endereco enderecoJoao2 = new Endereco(null, "Rua do teste2", "252", "casa", "bairro teste2", "9999-9999", joao, campinas);
		
		joao.getEnderecos().addAll(Arrays.asList(enderecoJoao,enderecoJoao2));
				
		Cliente pedroPinturasSA = new Cliente(null, "Pinturas SA", "pinturassa@email.com", "33333333333",TipoCliente.PESSOA_JURIDICA);
		pedroPinturasSA.getTelefones().addAll(Arrays.asList("8888-8888"));
		
		Endereco enderecoPedroPinturasSA = new Endereco(null, "Rua do teste3", "235", "Barracao", "bairro teste3", "8888-8888", pedroPinturasSA, saoPaulo);
		pedroPinturasSA.getEnderecos().addAll(Arrays.asList(enderecoPedroPinturasSA));
		
		clienteRepository.save(Arrays.asList(joao,pedroPinturasSA));
		enderecoRepository.save(Arrays.asList(enderecoJoao,enderecoJoao2,enderecoPedroPinturasSA));
		
		//cria um pedido
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		Pedido pedidoJoao = new Pedido(null, formatter.parse("30/09/2017 10:32:28"), 
				enderecoJoao, 
				joao);
				
		Pedido pedidoJoao2 = new Pedido(null, formatter.parse("01/10/2017 17:05:09"), 
				enderecoJoao2, 
				joao);
		
		//cria o pagamento do pedido
		Pagamento pgtoCartao = new PagamentoComCartao(null, EstadoPagamento.QUITADO, 
				pedidoJoao, 3);		
		pedidoJoao.setPagamento(pgtoCartao);
		
		Pagamento pgtoBoleto = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, 
				pedidoJoao2,formatter.parse("02/10/2017 23:59:59"), formatter.parse("01/10/2017 12:15:32"), 
				"89800107010137867161201255520");
		
		pedidoJoao2.setPagamento(pgtoBoleto);
		
		joao.getPedidos().addAll(Arrays.asList(pedidoJoao,pedidoJoao2));
		
		pedidoRepository.save(Arrays.asList(pedidoJoao,pedidoJoao2));
		pagamentoRepository.save(Arrays.asList(pgtoBoleto,pgtoCartao));
		
		//cria os itens dos pedidos
		ItemPedido itemPedidoJoao = new ItemPedido(pedidoJoao, mouse, 0.00, 1, mouse.getPreco());
		ItemPedido itemPedidoJoao2 = new ItemPedido(pedidoJoao, impressora, 20.00, 1, (impressora.getPreco() - 20.00));
		ItemPedido itemPedidoJoao3 = new ItemPedido(pedidoJoao2, computador, 0.00, 1, computador.getPreco());
		
		pedidoJoao.getItens().addAll(Arrays.asList(itemPedidoJoao,itemPedidoJoao2));
		pedidoJoao2.getItens().addAll(Arrays.asList(itemPedidoJoao3));
		
		mouse.getItens().addAll(Arrays.asList(itemPedidoJoao));
		impressora.getItens().addAll(Arrays.asList(itemPedidoJoao2));
		computador.getItens().addAll(Arrays.asList(itemPedidoJoao3));
		
		itemPedidoRepository.save(Arrays.asList(itemPedidoJoao,itemPedidoJoao2,itemPedidoJoao3));
	}
}
