package com.instagram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping("/")
	public String inicio(Model model, HttpSession session) {
		model.addAttribute("titulo", "Inicio");
		log.info("ID del usuario logueado: {}", session.getAttribute("idUsuario"));
		log.info("Bienvenido!");
		return "usuario/inicio";
	}
	
	@GetMapping("/login")
	public String vistaLogin() {
		return "login";
	}
	
	@PostMapping("/iniciarSesion")
	public String iniciarSesion() {
		return "redirect:/";
	}

}
