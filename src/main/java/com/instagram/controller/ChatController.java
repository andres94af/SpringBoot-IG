package com.instagram.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.instagram.model.Chat;
import com.instagram.model.Notificacion;
import com.instagram.model.Usuario;
import com.instagram.service.IChatService;
import com.instagram.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/direct")
public class ChatController {

	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IChatService chatService;

	// REDIRECCION A LA VISTA MENSAJES DEL USUARIO LOGUEADO
	@GetMapping("/inbox")
	public String inbox(Model model, HttpSession session) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
//		List<Usuario> usuariosChats = usuarioService.findUsuariosChats(usuarioLogueado.get());
		List<Chat> chats = usuarioLogueado.get().getChats();
		List<Notificacion> notificaciones = usuarioLogueado.get().getNotificaciones();
		List<Usuario> seguidos = usuarioService.findAllSeguidos(usuarioLogueado.get());
		notificaciones.removeIf(n -> n.isRecibida());
//		Chat chat = new Chat();
		model.addAttribute("title", "Bandeja de entrada  • Chats");
		model.addAttribute("usuarioLogueado", usuarioLogueado.get());
		model.addAttribute("notificaciones", notificaciones);
		model.addAttribute("seguidos", seguidos);
		model.addAttribute("chats", chats);
//		model.addAttribute("chat", chat);
		return "usuario/chats";
	}
	
	// REDIRECCION A LA VISTA MENSAJES DEL USUARIO LOGUEADO
	@GetMapping("/new/{id}")
	public String nuevoChat(Model model, HttpSession session, @PathVariable("id") Integer id) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Usuario> usuarioChat = usuarioService.findById(id);
		if (usuarioLogueado.isPresent() && usuarioChat.isPresent()) {
			List<Usuario> usuariosDelChat = new ArrayList<>();
			usuariosDelChat.add(usuarioLogueado.get());
			usuariosDelChat.add(usuarioChat.get());
			Chat chat = new Chat(usuariosDelChat, null);
			chatService.save(chat);
			List<Chat> chatsDelUsuarioLogueado = usuarioLogueado.get().getChats();
			List<Chat> chatsDelUsuarioSecundario = usuarioChat.get().getChats();
			chatsDelUsuarioLogueado.add(chat);
			chatsDelUsuarioSecundario.add(chat);
			usuarioLogueado.get().setChats(chatsDelUsuarioLogueado);
			usuarioChat.get().setChats(chatsDelUsuarioSecundario);
			usuarioService.update(usuarioLogueado.get());
			usuarioService.update(usuarioChat.get());
		}
		return "redirect:/direct/inbox";
	}

}
