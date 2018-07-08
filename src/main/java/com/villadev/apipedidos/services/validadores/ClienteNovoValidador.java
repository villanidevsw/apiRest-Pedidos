package com.villadev.apipedidos.services.validadores;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.dtos.ClienteNovoDTO;
import com.villadev.apipedidos.repositories.ClienteRepository;
import com.villadev.apipedidos.resources.exceptions.CampoMensagemException;
import com.villadev.apipedidos.services.validadores.utils.DocumentoBR;

public class ClienteNovoValidador implements ConstraintValidator<ClienteNovoValido, ClienteNovoDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteNovoValido anotacao) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objDto, ConstraintValidatorContext context) {
		
		List<CampoMensagemException> violacoes = new ArrayList<>();
		
		if (ClienteNovoDTO.ehPessoaFisica(objDto.getTipo()) && !DocumentoBR.ehValidoCPF(objDto.getCpfOuCnpj())) {
			violacoes.add(new CampoMensagemException("cpfOuCnpj", "CPF inválido"));
		}

		if (ClienteNovoDTO.ehPessoaJuridica(objDto.getTipo()) && !DocumentoBR.ehValidoCNPJ(objDto.getCpfOuCnpj())) {
			violacoes.add(new CampoMensagemException("cpfOuCnpj", "CNPJ inválido"));
		}

		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		if (cliente != null) {
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
