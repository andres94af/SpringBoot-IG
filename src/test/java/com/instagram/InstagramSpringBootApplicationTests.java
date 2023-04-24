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
		PasswordEncoder passEncoder = new BCryptPasswordEncoder();
		System.out.println(passEncoder.encode("123456"));
	}

}
