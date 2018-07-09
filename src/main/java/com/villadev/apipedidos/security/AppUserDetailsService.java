package com.villadev.apipedidos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.villadev.apipedidos.domain.Cliente;
import com.villadev.apipedidos.domain.enums.Perfil;
import com.villadev.apipedidos.repositories.ClienteRepository;
import com.villadev.apipedidos.resources.exceptions.AuthorizationException;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private ClienteRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repo.findByEmail(email);
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		return new AppUserDetails(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}
	
	public AppUserDetails getCurrentUserAuthenticated() {
		try {
			return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}

	public void usuarioTemPermissaoAdmin(Integer usuarioId) {
		AppUserDetails usuarioAtual = getCurrentUserAuthenticated();

		if (usuarioAtual == null || !usuarioAtual.hasRole(Perfil.ADMIN) && !usuarioId.equals(usuarioAtual.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
	}
	
}
