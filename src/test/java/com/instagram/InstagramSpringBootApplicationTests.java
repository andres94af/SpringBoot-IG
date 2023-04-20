package com.instagram;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.instagram.model.Autorizacion;
import com.instagram.model.Imagen;
import com.instagram.model.Usuario;
import com.instagram.service.IAutorizacionService;
import com.instagram.service.IImagenService;
import com.instagram.service.IUsuarioService;

@SpringBootTest
class InstagramSpringBootApplicationTests {
	
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IAutorizacionService autorizacionService;
	
	@Autowired
	IImagenService imagenService;

	@Test
	void contextLoads() {
		Usuario usuario = usuarioService.findById(1).get();
//		PasswordEncoder passEncoder = new BCryptPasswordEncoder();
//		Usuario usuario = new Usuario();
//		usuario.setNombre("Andres");
//		usuario.setApellido("Fernandez");
//		usuario.setEmail("andres94@gmail.com");
//		usuario.setFechaNacimiento(LocalDate.now());
//		usuario.setGenero("Masculino");
//		usuario.setPassword(passEncoder.encode("12345"));
//		usuario.setUsername("anndyfernandez");
//		usuarioService.save(usuario);
//		Autorizacion aut = new Autorizacion(1, usuario, true, "USER");
//		autorizacionService.save(aut);
//		usuario.setAutorizacion(aut);
//		usuarioService.save(usuario);
		Imagen imagen = new Imagen();
		imagen.setUsuario(usuario);
		imagen.setUrl("https://www.infojobs.net/ficha.foto?quina=e2ce1f62-2ba9-4596-b7e2-3e52757d9cab");
		imagenService.save(imagen);
		
	}

}
