package com.instagram.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.instagram.model.Autorizacion;
import com.instagram.model.Usuario;
import com.instagram.service.IAutorizacionService;
import com.instagram.service.IImagenService;
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
	
	@GetMapping("/")
	public String inicio(Model model, HttpSession session) {
		Optional<Usuario> usuarioOpt = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		log.info("Usuario logueado: {}",usuarioOpt.get());
		model.addAttribute("titulo", "Inicio del usuario: " + usuarioOpt.get().getUsername());
		return "usuario/inicio";
	}
	
	@GetMapping("/login")
	public String vistaLogin(Model model) {
		model.addAttribute("titulo", "Login");
		return "login";
	}
	
	@GetMapping("/registro")
	public String vistaRegistro(Model model) {
		model.addAttribute("titulo", "Registro");
		return "registro";
	}
	
	@PostMapping("/iniciarSesion")
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
