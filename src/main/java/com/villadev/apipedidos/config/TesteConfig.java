package com.villadev.apipedidos.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.villadev.apipedidos.services.emails.EmailService;
import com.villadev.apipedidos.services.emails.MockEmailService;
import com.villadev.apipedidos.services.utils.BDService;

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	private BDService bdService;
	
	@Bean
	public boolean popularBaseDeDados() throws ParseException {
		bdService.popularBaseDeDados();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
