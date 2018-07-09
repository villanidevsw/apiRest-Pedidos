package com.villadev.apipedidos.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.villadev.apipedidos.services.utils.BDService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private BDService bdService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String estrategiaHibernate;
	
	@Bean
	public boolean popularBaseDeDados() throws ParseException {
		
		if (!"create".equals(estrategiaHibernate)) {
			return false;
		}
		
		bdService.popularBaseDeDados();
		return true;
	}
}
