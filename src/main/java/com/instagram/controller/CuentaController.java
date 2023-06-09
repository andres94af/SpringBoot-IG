package com.instagram.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.instagram.model.Comentario;
import com.instagram.model.Imagen;
import com.instagram.model.Like;
import com.instagram.model.Notificacion;
import com.instagram.model.Publicacion;
import com.instagram.model.Seguido;
import com.instagram.model.Seguidor;
import com.instagram.model.Solicitud;
import com.instagram.model.TipoDeNotificacion;
import com.instagram.model.Usuario;
import com.instagram.service.IAutorizacionService;
import com.instagram.service.IComentarioService;
import com.instagram.service.IImagenService;
import com.instagram.service.ILikeService;
import com.instagram.service.INotificacionService;
import com.instagram.service.IPublicacionService;
import com.instagram.service.ISeguidoService;
import com.instagram.service.ISeguidorService;
import com.instagram.service.ISolicitudService;
import com.instagram.service.IUsuarioService;
import com.instagram.service.cloudinary.CloudinarySevice;

import jakarta.servlet.http.HttpSession;

//CONTROLADOR QUE GESTIONA LAS CUENTAS, LIKES, COMENTARIOS, NUEVAS PUBLICACIONES, SEGUIDORES, ETC.
@Controller
@RequestMapping("/cuenta")
@SuppressWarnings("rawtypes")
public class CuentaController {

	PasswordEncoder passEncoder = new BCryptPasswordEncoder();

	@Autowired
	CloudinarySevice cloudinarySevice;

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

	@Autowired
	ILikeService likeService;

	@Autowired
	ISeguidoService seguidoService;

	@Autowired
	ISeguidorService seguidorService;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	ISolicitudService solicitudService;

	// METOGO QUE AGREGA UN COMENTARIO A UNA PUBLICACION
	@PostMapping("/agregarComentario/{id}")
	public String agragarComentario(@RequestParam String comentario, @PathVariable Integer id, HttpSession session) {
		if (comentario.equals("")) {
			return "redirect:/";
		} else {
			Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
			Publicacion publicacion = publicacionService.findById(id).get();
			Comentario nuevoComentario = new Comentario(usuarioLogueado.get(), LocalDate.now(), comentario,
					publicacion);
			if (!usuarioLogueado.get().equals(publicacion.getUsuario())) {
				Notificacion notificacion = new Notificacion(usuarioLogueado.get(), TipoDeNotificacion.COMENTARIO,
						LocalDate.now(), publicacion.getUsuario(), false, publicacion);
				notificacionService.save(notificacion);
			}
			comentarioService.save(nuevoComentario);
			return "redirect:/#post" + id;
		}
	}

	// METOGO QUE AGREGA UN LIKE A UNA PUBLICACION, O LO QUITA SI YA LO TIENE
	@GetMapping("/agregarQuitarLike/{id}")
	public String agragarQuitarLike(@PathVariable Integer id, HttpSession session) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Publicacion publicacion = publicacionService.findById(id).get();
		Like like = new Like(usuarioLogueado.get(), publicacion);
		List<Like> likes = likeService.findAll();
		for (Like l : likes) {
			if (l.getUsuario().getUsername().equals(like.getUsuario().getUsername())
					&& l.getPublicacion().getId().equals(like.getPublicacion().getId())) {
				likeService.delete(l.getId());
				return "redirect:/#post" + id;
			}
		}
		if (!usuarioLogueado.get().equals(publicacion.getUsuario())) {
			Notificacion notificacion = new Notificacion(usuarioLogueado.get(), TipoDeNotificacion.LIKE,
					LocalDate.now(), publicacion.getUsuario(), false, publicacion);
			notificacionService.save(notificacion);
		}
		likeService.save(like);
		return "redirect:/#post" + id;
	}

	@PostMapping("/crearPublicacion")
	public String crearNuevaPublicacion(HttpSession session, Publicacion publicacion,
			@RequestParam("img") MultipartFile file, @RequestParam("efecto") String efecto) throws IOException {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		publicacion.setUsuario(usuarioLogueado.get());
		publicacion.setFechaCreacion(LocalDate.now());
		publicacionService.save(publicacion);
		BufferedImage bi = ImageIO.read(file.getInputStream());
		if (bi == null)
			return "redirect:/?img_error";
		Map result = cloudinarySevice.upload(file, efecto, "instagram/img_publicacion");
		Imagen imagenPublicacion = new Imagen((String) result.get("url"), (String) result.get("public_id"),
				usuarioLogueado.get(), publicacion);
		imagenService.save(imagenPublicacion);
		return "redirect:/#post" + publicacion.getId();
	}

	@PostMapping("/actualizarFotoPerfil")
	public String actualizarFotoDeFerfil(HttpSession session, @RequestParam("img") MultipartFile file,
			@RequestParam("efecto") String efecto) throws IOException {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Imagen imgPerfilAct = usuarioLogueado.get().getImgPerfil();
		BufferedImage bi = ImageIO.read(file.getInputStream());
		if (bi == null)
			return "redirect:/" + usuarioLogueado.get().getUsername() + "/?img_error";
		Map result = cloudinarySevice.upload(file, efecto, "instagram/img_perfil");
		Imagen nuevaImgPerfil = new Imagen((String) result.get("url"), (String) result.get("public_id"),
				usuarioLogueado.get(), null);
		imagenService.save(nuevaImgPerfil);
		usuarioLogueado.get().setImgPerfil(nuevaImgPerfil);
		usuarioService.update(usuarioLogueado.get());
		imagenService.deleteById(imgPerfilAct.getId());
		if (!imgPerfilAct.getImgId().equals("hlqmcwbljw9ymsfopwkn"))
			cloudinarySevice.delete(imgPerfilAct.getImgId());
		return "redirect:/perfil/" + usuarioLogueado.get().getUsername() + "/p";
	}

	@PostMapping("/actualizarDatos")
	public String actualizarDatosDelUsuario(@RequestParam Integer id, @RequestParam String username,
			@RequestParam String password, @RequestParam String info1, @RequestParam String info2,
			@RequestParam Optional<String> esPublico) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		if (!password.equals(""))
			usuario.get().setPassword(passEncoder.encode(password));
		List<Usuario> usuarios = usuarioService.findAll();
		usuarios.removeIf(u -> u.getUsername().equals(usuario.get().getUsername()));
		for (Usuario u : usuarios) {
			if (u.getUsername().equals(username))
				return "redirect:/perfil/" + usuario.get().getUsername() + "/p?existe_u";
		}
		usuario.get().setUsername(username);
		usuario.get().setInfo1(info1);
		usuario.get().setInfo2(info2);
		if (esPublico.isEmpty()) {
			usuario.get().setPerfilPublico(false);
		} else {
			usuario.get().setPerfilPublico(true);
		}
		usuarioService.update(usuario.get());
		System.out.println(usuario);
		return "redirect:/perfil/" + usuario.get().getUsername() + "/p?act_e";
	}

	@GetMapping("/enviarSolicitud/{id}")
	public String enviarSolicitud(HttpSession session, @PathVariable("id") Integer id) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Usuario> usuarioPerfil = usuarioService.findById(id);
		if (usuarioLogueado.isPresent() && usuarioPerfil.isPresent()) {
			List<Solicitud> solicitudes = solicitudService.findAll();
			for (Solicitud s : solicitudes) {
				if (s.getEmisor().equals(usuarioLogueado.get()) && s.getDestinatario().equals(usuarioPerfil.get())) {
					return "redirect:/perfil/" + usuarioPerfil.get().getUsername() + "/p";
				}
			}
			if (usuarioPerfil.get().isPerfilPublico()) {
				seguir(session, id);
				Notificacion notificacion = new Notificacion(usuarioLogueado.get(), TipoDeNotificacion.SEGUIDO,
						LocalDate.now(), usuarioPerfil.get(), false);
				notificacionService.save(notificacion);
				return "redirect:/perfil/" + usuarioPerfil.get().getUsername() + "/p";
			} else {
				Solicitud solicitud = new Solicitud(usuarioLogueado.get(), LocalDate.now(), usuarioPerfil.get(), false);
				solicitudService.save(solicitud);
				Notificacion notificacion = new Notificacion(usuarioLogueado.get(), TipoDeNotificacion.SOLICITUD,
						LocalDate.now(), usuarioPerfil.get(), false, solicitud);
				notificacionService.save(notificacion);
			}
		}
		return "redirect:/perfil/" + usuarioPerfil.get().getUsername() + "/p";
	}

	@GetMapping("/aceptarSolicitud/{idNotificacion}")
	public String AceptarSolicitud(HttpSession session, @PathVariable("idNotificacion") Integer idNotificacion) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Notificacion> notificacionOpt = notificacionService.findById(idNotificacion);
		if (usuarioLogueado.isPresent() && notificacionOpt.isPresent()) {
			notificacionService.delete(notificacionOpt.get().getId());
			solicitudService.delete(notificacionOpt.get().getSolicitud().getId());
			Notificacion notificacion = new Notificacion(usuarioLogueado.get(), TipoDeNotificacion.SOLICITUD_ACEPTADA,
					LocalDate.now(), notificacionOpt.get().getEmisor(), false);
			notificacionService.save(notificacion);
			seguidorService.nuevoSeguidor(notificacionOpt.get().getEmisor(), usuarioLogueado.get());
			seguidoService.nuevoSeguido(usuarioLogueado.get(), notificacionOpt.get().getEmisor());
		}
		return "redirect:/";
	}

	@GetMapping("/eliminarSolicitud/{idNotificacion}")
	public String eliminarSolicitud(HttpSession session, @PathVariable("idNotificacion") Integer idNotificacion) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Notificacion> notificacionOpt = notificacionService.findById(idNotificacion);
		if (usuarioLogueado.isPresent() && notificacionOpt.isPresent()) {
			notificacionService.delete(notificacionOpt.get().getId());
			solicitudService.delete(notificacionOpt.get().getSolicitud().getId());
		}
		return "redirect:/";
	}

	public void seguir(HttpSession session, Integer id) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Usuario> usuarioPerfil = usuarioService.findById(id);
		if (usuarioLogueado.isPresent() && usuarioPerfil.isPresent()) {
			if (!usuarioLogueado.get().equals(usuarioPerfil.get())) {
				seguidorService.nuevoSeguidor(usuarioLogueado.get(), usuarioPerfil.get());
				seguidoService.nuevoSeguido(usuarioPerfil.get(), usuarioLogueado.get());
			}
		}
	}

	@GetMapping("/dejarDeSeguir/{id}")
	public String dejarDeSeguir(HttpSession session, @PathVariable("id") Integer id) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Usuario> usuarioVisitado = usuarioService.findById(id);
		List<Seguido> seguidos = seguidoService.findAll();
		List<Seguidor> seguidores = seguidorService.findAll();
		if (usuarioLogueado.isPresent() && usuarioVisitado.isPresent()) {
			for (Seguidor seguidor : seguidores) {
				if (seguidor.getNombre().equals(usuarioLogueado.get())
						&& seguidor.getUsuario().equals(usuarioVisitado.get())) {
					seguidorService.deleteById(seguidor.getId());
				}
			}
			for (Seguido seguido : seguidos) {
				if (seguido.getNombre().equals(usuarioVisitado.get())
						&& seguido.getUsuario().equals(usuarioLogueado.get())) {
					seguidoService.deleteById(seguido.getId());
				}
			}
		}
		return "redirect:/perfil/" + usuarioVisitado.get().getUsername() + "/p";
	}

	@GetMapping("/notificacionVista/{idNotificacion}")
	public String notificacionVista(@PathVariable("idNotificacion") Integer idNotificacion) {
		Optional<Notificacion> n = notificacionService.findById(idNotificacion);
		if (n.isPresent()) {
			n.get().setRecibida(true);
			notificacionService.update(n.get());
			if (n.get().getTipo().equals(TipoDeNotificacion.COMENTARIO) || n.get().getTipo().equals(TipoDeNotificacion.LIKE)) {
				return "redirect:/#post" + n.get().getPublicacion().getId();				
			}
		}
		return "redirect:/";
	}

}
