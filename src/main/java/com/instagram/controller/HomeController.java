package com.instagram.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/")
	public String inicio(Model model, HttpSession session) {
		Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		List<Usuario> usuarios = usuarioService.findAll();
		List<Publicacion> publicaciones = publicacionService.findAll();
		model.addAttribute("titulo", usuario.getUsername());
		model.addAttribute("usuario", usuario);
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("publicaciones", publicaciones);
		log.info("Listado de publicaciones: {}", publicaciones);
		return "usuario/inicio";
	}
	
	@GetMapping("/login")
	public String vistaLogin(Model model, HttpSession session) {
		if (session.getAttribute("idUsuario")!=null) {
			model.addAttribute("titulo", "Login");
			return "redirect:/";
		}
		return "login";
	}
	
	@GetMapping("/registro")
	public String vistaRegistro(Model model) {
		model.addAttribute("titulo", "Registro");
		return "registro";
	}
	
	@GetMapping("/iniciarSesion")
	public String iniciarSesion() {
		return "redirect:/";
	}
	
	@GetMapping("/generarCuenta")
	public String guardarNuevaCuenta(Usuario usuario) {
		Optional<Usuario> usuarioEmail = usuarioService.findByEmail(usuario.getEmail());
		Optional<Usuario> usuarioUsername = usuarioService.findByUsername(usuario.getUsername());
		if (usuarioEmail.isPresent()) {
			return "redirect:/registro?existe_m";
		}else if (usuarioUsername.isPresent()) {
			return "redirect:/registro?existe_u";
		}else {
		usuario.setPassword(passEncoder.encode(usuario.getPassword()));
		usuarioService.save(usuario);
		return "redirect:/login";
		}
	}

}
