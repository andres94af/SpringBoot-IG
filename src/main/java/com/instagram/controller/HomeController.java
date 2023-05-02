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
		Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		model.addAttribute("usuario", usuario);// usuario logueado
		List<Usuario> usuariosHistorias = usuarioService.findAllSeguidos(usuario);
		model.addAttribute("historias", usuariosHistorias);// usuarios seguidos, historias
		List<Usuario> usuariosSugeridos = usuarioService.findAllNoSeguidos(usuario);
		model.addAttribute("sugerencias", usuariosSugeridos);// usuarios NO seguidos sugerencias
		List<Publicacion> publicaciones = publicacionService.findAll();
		model.addAttribute("publicaciones", publicaciones);// public de gente que sigo(VER)
		return "usuario/inicio";
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

	// METODO QUE INICIA SESION
	@GetMapping("/iniciarSesion")
	public String iniciarSesion() {
		return "redirect:/";
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

	// METODO QUE LLEVA A LA VISTA DE PERFIL/PUBLICACIONES DEL USUARIO SELECCIONADO
	@GetMapping("/{username}/")
	public String perfilConVistaEnPublicaciones(Model model, HttpSession session, @PathVariable String username) {
		Usuario usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		Optional<Usuario> usuarioPerfil = usuarioService.findByUsername(username);
		List<Seguidor> seguidoresDelPerfil = usuarioPerfil.get().getSeguidores();
		if (!usuarioPerfil.isEmpty()) {
			Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
			model.addAttribute("title", " @" + username);
			model.addAttribute("usuario", usuario);
			model.addAttribute("usuarioPerfil", usuarioPerfil.get());
			List<Publicacion> publicacionesDelPerfil = publicacionService.findByUsuario(usuarioPerfil.get());
			model.addAttribute("publicaciones", publicacionesDelPerfil);
			model.addAttribute("vista", 1);
			for (Seguidor s : seguidoresDelPerfil) {
				if (s.getNombre().equals(usuarioLogueado)) {
					model.addAttribute("seguido", true);
					break;
				}
			}
			return "usuario/perfil";
		} else {
			return "redirect:/";
		}
	}

	// METODO QUE LLEVA A LA VISTA DE REELS DEL USUARIO SELECCIONADO
	@GetMapping("/{username}/reels")
	public String perfilConVistaEnReels(Model model, HttpSession session, @PathVariable String username) {
		Usuario usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		Optional<Usuario> usuarioPerfil = usuarioService.findByUsername(username);
		List<Seguidor> seguidoresDelPerfil = usuarioPerfil.get().getSeguidores();
		if (!usuarioPerfil.isEmpty()) {
			Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
			model.addAttribute("title", " @" + username);
			model.addAttribute("usuario", usuario);
			model.addAttribute("usuarioPerfil", usuarioPerfil.get());
			List<Publicacion> publicacionesDelPerfil = publicacionService.findByUsuario(usuarioPerfil.get());
			publicacionesDelPerfil.removeIf(p -> p.getTipo().equals(TipoDePublicacion.PUBLICACION));
			model.addAttribute("publicaciones", publicacionesDelPerfil);
			model.addAttribute("vista", 2);
			for (Seguidor s : seguidoresDelPerfil) {
				if (s.getNombre().equals(usuarioLogueado)) {
					model.addAttribute("seguido", true);
					break;
				}
			}
			return "usuario/perfil";
		} else {
			return "redirect:/";
		}
	}

	// METODO QUE LLEVA A LA VISTA DE PUBLICACIONES GUARDADAS POR EL USUARIO
	// LOGUEADO
	// <----FALTA TERMINAR
	@GetMapping("/{username}/saved")
	public String perfilConVistaEnPublicacionesGuardadas(Model model, HttpSession session,
			@PathVariable String username) {
		Optional<Usuario> usuarioPerfil = usuarioService.findByUsername(username);
		if (!usuarioPerfil.isEmpty()) {
			Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
			model.addAttribute("title", " @" + username);
			model.addAttribute("usuario", usuario);
			model.addAttribute("usuarioPerfil", usuarioPerfil.get());
			List<Publicacion> publicacionesDelPerfil = publicacionService.findByUsuario(usuarioPerfil.get());
			// aqui la logica de publicaciones guardadas
			model.addAttribute("publicaciones", publicacionesDelPerfil);
			model.addAttribute("vista", 3);
			return "usuario/perfil";
		} else {
			return "redirect:/";
		}
	}

	// METODO QUE LLEVA A LA VISTA DE PUBLICACIONES GUARDADAS DEL USUARIO
	// SELECCIONADO <----FALTA TERMINAR
	@GetMapping("/{username}/tagged")
	public String perfilConVistaEnPublicacionesEtiquetado(Model model, HttpSession session,
			@PathVariable String username) {
		Usuario usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		Optional<Usuario> usuarioPerfil = usuarioService.findByUsername(username);
		List<Seguidor> seguidoresDelPerfil = usuarioPerfil.get().getSeguidores();
		if (!usuarioPerfil.isEmpty()) {
			Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
			model.addAttribute("title", " @" + username);
			model.addAttribute("usuario", usuario);
			model.addAttribute("usuarioPerfil", usuarioPerfil.get());
			List<Publicacion> publicacionesDelPerfil = publicacionService.findByUsuario(usuarioPerfil.get());
			// aqui la logica de publicaciones en las que me etiquetaron
			model.addAttribute("publicaciones", publicacionesDelPerfil);
			model.addAttribute("vista", 4);
			for (Seguidor s : seguidoresDelPerfil) {
				if (s.getNombre().equals(usuarioLogueado)) {
					model.addAttribute("seguido", true);
					break;
				}
			}
			return "usuario/perfil";
		} else {
			return "redirect:/";
		}
	}

}
