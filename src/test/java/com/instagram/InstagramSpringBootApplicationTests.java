package com.instagram;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.instagram.model.Autorizacion;
import com.instagram.model.Imagen;
import com.instagram.model.Publicacion;
import com.instagram.model.Seguido;
import com.instagram.model.Seguidor;
import com.instagram.model.Usuario;
import com.instagram.service.IAutorizacionService;
import com.instagram.service.IImagenService;
import com.instagram.service.IPublicacionService;
import com.instagram.service.ISeguidoService;
import com.instagram.service.ISeguidorService;
import com.instagram.service.IUsuarioService;

@SpringBootTest
class InstagramSpringBootApplicationTests {

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	IAutorizacionService autorizacionService;

	@Autowired
	IImagenService imagenService;

	@Autowired
	IPublicacionService publicacionService;

	@Autowired
	ISeguidoService seguidoService;

	@Autowired
	ISeguidorService seguidorService;

	@Test
	void contextLoads() {
		Usuario usuario = usuarioService.findByUsername("anndyfernandez").get();
		usuario.setPerfilPublico(true);
		usuarioService.update(usuario);
	}

}
