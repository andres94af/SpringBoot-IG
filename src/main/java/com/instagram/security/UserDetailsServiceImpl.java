package com.instagram.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.instagram.model.Usuario;
import com.instagram.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;

@Service
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
			return new UserDetailsImpl(usuarioOpt.get());
		} else {
			throw new UsernameNotFoundException("El usuario con email " + email + " no existe.");
		}
	}

}