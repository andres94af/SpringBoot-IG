package com.instagram.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.instagram.model.Comentario;
import com.instagram.model.Publicacion;
import com.instagram.model.Usuario;
import com.instagram.service.IAutorizacionService;
import com.instagram.service.IComentarioService;
import com.instagram.service.IImagenService;
import com.instagram.service.IPublicacionService;
import com.instagram.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

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

	@Autowired
	IComentarioService comentarioService;

	@Autowired
	IPublicacionService publicacionService;

	@PostMapping("/agregarComentario/{id}")
	public String agragarComentario(@RequestParam String comentario, @PathVariable Integer id, HttpSession session) {
		if (comentario.equals("")) {
			return "redirect:/";
		} else {
			log.info("Comentario recibido: {}", comentario);
			log.info("Id de la publicacion: {}", id);
			Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
			Publicacion publicacion = publicacionService.findById(id).get();
			Comentario nuevoComentario = new Comentario(usuario, LocalDate.now(), comentario, publicacion);
			comentarioService.save(nuevoComentario);
			return "redirect:/";
		}
	}

}
