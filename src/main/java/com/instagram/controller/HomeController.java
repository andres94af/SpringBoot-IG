package com.instagram.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.instagram.model.Notificacion;
import com.instagram.model.Publicacion;
import com.instagram.model.Seguidor;
import com.instagram.model.TipoDePublicacion;
import com.instagram.model.Usuario;
import com.instagram.service.IImagenService;
import com.instagram.service.IPublicacionService;
import com.instagram.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;

//CONTROLADOR QUE REDIRECCIONA A LAS VISTAS Y CARGA CONTENIDO
@Controller
@RequestMapping("")
public class HomeController {

	PasswordEncoder passEncoder = new BCryptPasswordEncoder();

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	IImagenService imagenService;

	@Autowired
	IPublicacionService publicacionService;

	// REDIRECCION AL INICIO SI SE ENCUENTRA LOGUEADO
	@GetMapping("/")
	public String inicio(Model model, HttpSession session) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		List<Usuario> usuariosHistorias = usuarioService.findAllSeguidos(usuarioLogueado.get());
		List<Usuario> usuariosSugeridos = usuarioService.findAllNoSeguidos(usuarioLogueado.get());
		List<Publicacion> publicaciones = publicacionService.publicacionesQueSigo(usuarioLogueado.get());
		List<Notificacion> notificaciones = usuarioLogueado.get().getNotificaciones();
		notificaciones.removeIf(n -> n.isRecibida());
		model.addAttribute("usuarioLogueado", usuarioLogueado.get());
		model.addAttribute("historias", usuariosHistorias);
		model.addAttribute("sugerencias", usuariosSugeridos);
		model.addAttribute("publicaciones", publicaciones);
		model.addAttribute("notificaciones", notificaciones);
		return "usuario/inicio";
	}

	// METODO QUE LLEVA A LA VISTA DE PERFIL DEL USUARIO SELECCIONADO
	@GetMapping("/perfil/{username}/{vista}")
	public String perfilConVistaEnPublicaciones(Model model, HttpSession session, @PathVariable String username,
			@PathVariable String vista) {
		Optional<Usuario> usuarioPerfil = usuarioService.findByUsername(username);
		if (!usuarioPerfil.isEmpty()) {
			Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
			List<Publicacion> publicacionesDelPerfil = publicacionService.findByUsuario(usuarioPerfil.get());
			List<Notificacion> notificaciones = usuarioLogueado.get().getNotificaciones();
			notificaciones.removeIf(n -> n.isRecibida());
			boolean perfilVisible = usuarioService.perfilVisible(usuarioLogueado.get(), usuarioPerfil.get());
			model.addAttribute("title", " @" + username);
			model.addAttribute("usuarioLogueado", usuarioLogueado.get());
			model.addAttribute("usuarioPerfil", usuarioPerfil.get());
			switch (vista) {
			case "reels":
				publicacionesDelPerfil.removeIf(p -> p.getTipo().equals(TipoDePublicacion.PUBLICACION));
				model.addAttribute("publicaciones", publicacionesDelPerfil);
				model.addAttribute("vista", 2);
				break;
			case "saved":
				// AQUI EL FILTRO PARA PUBLIC GUARDADAS
				model.addAttribute("publicaciones", publicacionesDelPerfil);
				model.addAttribute("vista", 3);
				break;
			default:
				model.addAttribute("publicaciones", publicacionesDelPerfil);
				model.addAttribute("vista", 1);
				break;
			}
			model.addAttribute("perfilVisible", perfilVisible);
			model.addAttribute("notificaciones", notificaciones);
			for (Seguidor s : usuarioPerfil.get().getSeguidores()) {
				if (s.getNombre().equals(usuarioLogueado.get())) {
					model.addAttribute("seguido", true);
					break;
				}
			}
			return "usuario/perfil";
		} else {
			return "redirect:/";
		}
	}

	// REDIRECCION A LOGUIN. SI ESTA LOGUEADO REDIRECCIONA AL INICIO
	@GetMapping("/login")
	public String vistaLogin(Model model, Authentication auth) {
		model.addAttribute("title", " - Login");
		if (auth != null) {
			return "redirect:/";
		}
		return "login";
	}

	// REDIRECCION A VISTA REGISTRO
	@GetMapping("/registro")
	public String vistaRegistro(Model model) {
		model.addAttribute("title", " - Registro");
		return "registro";
	}

	// METODO QUE REGISTRA NUEVO USUARIO EN BBDD
	@GetMapping("/generarCuenta")
	public String guardarNuevaCuenta(Usuario usuario) {
		Optional<Usuario> usuarioEmail = usuarioService.findByEmail(usuario.getEmail());
		Optional<Usuario> usuarioUsername = usuarioService.findByUsername(usuario.getUsername());
		if (usuarioEmail.isPresent()) {
			return "redirect:/registro?existe_m";
		} else if (usuarioUsername.isPresent()) {
			return "redirect:/registro?existe_u";
		} else {
			usuario.setPassword(passEncoder.encode(usuario.getPassword()));
			usuarioService.save(usuario);
			return "redirect:/login";
		}
	}

	// METODO QUE INICIA SESION
	@GetMapping("/iniciarSesion")
	public String iniciarSesion() {
		return "redirect:/";
	}

}
