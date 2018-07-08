package com.villadev.apipedidos.services.validadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.dtos.ClienteDTO;
import com.villadev.apipedidos.repositories.ClienteRepository;
import com.villadev.apipedidos.resources.exceptions.CampoMensagemException;

public class ClienteAtualizadoValidador implements ConstraintValidator<ClienteAtualizadoValido, ClienteDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteAtualizadoValido anotacao) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		List<CampoMensagemException> violacoes = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		if (cliente != null && !cliente.getId().equals(uriId)) {
			violacoes.add(new CampoMensagemException("email", "Email já existente"));
		}
		
		violacoes.stream().forEach(v -> {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(v.getMensagem()).addPropertyNode(v.getCampo())
					.addConstraintViolation();
		});
		
		return violacoes.isEmpty();
	}


}
