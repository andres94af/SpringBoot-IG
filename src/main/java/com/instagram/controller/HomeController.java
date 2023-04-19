package com.instagram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {
	
	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("titulo", "Hola desde Spring Boot IG");
		return "usuario/HTML_BASE";
	}
	
	@GetMapping("/login1")
	public String inicio() {
		return "login";
	}

}
