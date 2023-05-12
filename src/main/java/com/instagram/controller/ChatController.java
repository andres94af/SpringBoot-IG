package com.instagram.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.instagram.model.Notificacion;
import com.instagram.model.Usuario;
import com.instagram.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/direct")
public class ChatController {

	@Autowired
	IUsuarioService usuarioService;

	// REDIRECCION A LA VISTA MENSAJES DEL USUARIO LOGUEADO
	@GetMapping("/inbox")
	public String inbox(Model model, HttpSession session) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		List<Usuario> chats = usuarioService.findAllSeguidos(usuarioLogueado.get());
		List<Notificacion> notificaciones = usuarioLogueado.get().getNotificaciones();
		notificaciones.removeIf(n -> n.isRecibida());
		model.addAttribute("title", "Bandeja de entrada  • Chats");
		model.addAttribute("usuarioLogueado", usuarioLogueado.get());
		model.addAttribute("notificaciones", notificaciones);
		model.addAttribute("chats", chats);
		return "usuario/chats";
	}

}
