package com.instagram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.instagram.service.IAutorizacionService;
import com.instagram.service.IImagenService;
import com.instagram.service.IUsuarioService;


@Controller
@RequestMapping("/cuenta")
public class CuentaController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IAutorizacionService autorizacionService;
	
	@Autowired
	IImagenService imagenService;
	


}
