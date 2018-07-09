package com.villadev.apipedidos.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.villadev.apipedidos.domain.dtos.EmailDTO;
import com.villadev.apipedidos.security.AppUserDetails;
import com.villadev.apipedidos.security.AppUserDetailsService;
import com.villadev.apipedidos.security.JWTUtil;
import com.villadev.apipedidos.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AutenticacaoResource {
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		AppUserDetails usuarioAtual = appUserDetailsService.getCurrentUserAuthenticated();
		jwtUtil.generateResponseTokenHttp(response, usuarioAtual.getUsername());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
