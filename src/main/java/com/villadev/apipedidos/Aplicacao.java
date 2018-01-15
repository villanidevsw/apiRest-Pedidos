package com.villadev.apipedidos;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.domain.Produto;
import com.villadev.apipedidos.repositories.CategoriaRepository;
import com.villadev.apipedidos.repositories.ProdutoRepository;

@SpringBootApplication
public class Aplicacao implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(Aplicacao.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Categoria categoriaInformatica = new Categoria(null,"Informática");
		Categoria categoriaEscritorio = new Categoria(null,"Escritório");
		
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
	}
}
