package com.instagram.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.instagram.model.Usuario;
import com.instagram.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Component
class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);
		if (usuarioOpt.isPresent()) {
			session.setAttribute("idUsuario", usuarioOpt.get().getId());
			Usuario usuario = usuarioOpt.get();
			return User.builder().username(usuario.getUsername()).password(usuario.getPassword())
					.roles(usuario.getAutorizacion().getRol()).build();
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
	}

}