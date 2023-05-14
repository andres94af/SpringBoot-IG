package com.instagram.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.instagram.model.Chat;
import com.instagram.model.Mensaje;
import com.instagram.model.Notificacion;
import com.instagram.model.Usuario;
import com.instagram.service.IChatService;
import com.instagram.service.IMensajeService;
import com.instagram.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/direct")
public class ChatController {

	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	IChatService chatService;
	
	@Autowired
	IMensajeService mensajeService;

	// REDIRECCION A LA VISTA MENSAJES DEL USUARIO LOGUEADO
	@GetMapping("/inbox")
	public String inbox(Model model, HttpSession session) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		List<Chat> chats = usuarioLogueado.get().getChats();
		List<Notificacion> notificaciones = usuarioLogueado.get().getNotificaciones();
		List<Usuario> seguidos = usuarioService.findAllSeguidos(usuarioLogueado.get());
		notificaciones.removeIf(n -> n.isRecibida());
		model.addAttribute("title", "Bandeja de entrada • Chats");
		model.addAttribute("usuarioLogueado", usuarioLogueado.get());
		model.addAttribute("notificaciones", notificaciones);
		model.addAttribute("seguidos", seguidos);
		model.addAttribute("chats", chats);
		return "usuario/chats";
	}
	
	// REDIRECCION A LA VISTA MENSAJES DEL USUARIO CON UN CHAT ABIERTO
	@GetMapping("/t/{chatId}")
	public String abrirChat(Model model, HttpSession session, @PathVariable("chatId") String id) {
		try {
			Optional<Chat> chatAbierto = chatService.findById(Integer.valueOf(id));			
			if (chatAbierto.isPresent()) {
				inbox(model, session);
				List<Mensaje> mensajes = chatAbierto.get().getMensajes();
				model.addAttribute("title", "Spring Boot IG • Chats");
				model.addAttribute("chat", chatAbierto.get());	
				model.addAttribute("mensajes", mensajes);			
				return "usuario/chats";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "redirect:/direct/inbox";
	}
	
	// REDIRECCION A LA VISTA MENSAJES DEL USUARIO LOGUEADO
	@GetMapping("/new/{id}")
	public String nuevoChat(Model model, HttpSession session, @PathVariable("id") Integer id) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Usuario> usuarioChat = usuarioService.findById(id);
		List<Usuario> usuariosDelChat = new ArrayList<>();
		Chat chat = new Chat(usuariosDelChat, null);
		if (usuarioLogueado.isPresent() && usuarioChat.isPresent()) {
			usuariosDelChat.add(usuarioLogueado.get());
			usuariosDelChat.add(usuarioChat.get());
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
		return "redirect:/direct/t/"+chat.getId();
	}
	
	// REDIRECCION A LA VISTA MENSAJES DEL USUARIO LOGUEADO
	@PostMapping("/send")
	public String enviarMensaje(HttpSession session, @RequestParam("mensaje") String mensaje, @RequestParam("chatId") Integer chatId) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Chat> chat = chatService.findById(chatId);
		if (usuarioLogueado.isPresent() && chat.isPresent() && mensaje!="") {
			Mensaje msjNuevo = new Mensaje(usuarioLogueado.get(), chat.get(), mensaje);
			mensajeService.save(msjNuevo);
		}
		return "redirect:/direct/t/"+chatId;
	}

}
