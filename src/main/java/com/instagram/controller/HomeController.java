package com.instagram.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.instagram.model.Like;
import com.instagram.model.Publicacion;
import com.instagram.model.Usuario;
import com.instagram.service.IImagenService;
import com.instagram.service.IPublicacionService;
import com.instagram.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	PasswordEncoder passEncoder = new BCryptPasswordEncoder();

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	IImagenService imagenService;

	@Autowired
	IPublicacionService publicacionService;

	// REDIRECCION AL INICIO SI SE ENCUENTRA LOGUEADO
	@GetMapping("/")
	public String inicio(Model model, HttpSession session) {
		Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		model.addAttribute("title", usuario.getUsername());
		model.addAttribute("usuario", usuario);// usuario logueado
		List<Usuario> usuarios = usuarioService.findAll();
		usuarios.removeIf(u -> u.equals(usuario));
		model.addAttribute("usuarios", usuarios);// seguidos(VER)
		List<Publicacion> publicaciones = publicacionService.findAll();
		model.addAttribute("publicaciones", publicaciones);// public de gente que sigo(VER)
		return "usuario/inicio";
	}

	// REDIRECCION A LOGUIN. SI ESTA LOGUEADO REDIRECCIONA AL INICIO
	@GetMapping("/login")
	public String vistaLogin(Model model, Authentication auth) {
		model.addAttribute("title", "Login");
		if (auth != null) {
			return "redirect:/";
		}
		return "login";
	}

	// REDIRECCION A VISTA REGISTRO
	@GetMapping("/registro")
	public String vistaRegistro(Model model) {
		model.addAttribute("title", "Registro");
		return "registro";
	}

	// METODO QUE INICIA SESION
	@GetMapping("/iniciarSesion")
	public String iniciarSesion() {
		return "redirect:/";
	}

	// METODO QUE REGISTRA NUEVO USUARIO EN BBDD
	@GetMapping("/generarCuenta")
	public String guardarNuevaCuenta(Usuario usuario) {
		Optional<Usuario> usuarioEmail = usuarioService.findByEmail(usuario.getEmail());
		Optional<Usuario> usuarioUsername = usuarioService.findByUsername(usuario.getUsername());
		if (usuarioEmail.isPresent()) {
			return "redirect:/registro?existe_m";
		} else if (usuarioUsername.isPresent()) {
			return "redirect:/registro?existe_u";
		} else {
			usuario.setPassword(passEncoder.encode(usuario.getPassword()));
			usuarioService.save(usuario);
			return "redirect:/login";
		}
	}

}
