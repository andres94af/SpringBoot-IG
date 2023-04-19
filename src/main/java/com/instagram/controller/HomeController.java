package com.instagram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("titulo", "Hola desde Spring Boot IG");
		return "usuario/HTML_BASE";
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
