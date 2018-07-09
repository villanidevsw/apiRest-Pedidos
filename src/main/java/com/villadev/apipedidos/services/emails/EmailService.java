package com.villadev.apipedidos.services.emails;

import org.springframework.mail.SimpleMailMessage;

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
