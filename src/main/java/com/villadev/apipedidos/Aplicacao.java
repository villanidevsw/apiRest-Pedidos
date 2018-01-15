package com.villadev.apipedidos;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.villadev.apipedidos.domain.Categoria;
import com.villadev.apipedidos.repositories.CategoriaRepository;

@SpringBootApplication
public class Aplicacao implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Aplicacao.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Categoria informatica = new Categoria(null,"Informática");
		Categoria escritorio = new Categoria(null,"Escritório");
		
		categoriaRepository.save(Arrays.asList(informatica,escritorio));		
	
	}
}
