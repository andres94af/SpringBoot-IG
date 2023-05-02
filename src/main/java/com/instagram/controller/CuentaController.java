package com.instagram.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.instagram.model.Publicacion;
import com.instagram.model.Seguido;
import com.instagram.model.Seguidor;
import com.instagram.model.Usuario;
import com.instagram.service.CloudinarySevice;
import com.instagram.service.IAutorizacionService;
import com.instagram.service.IComentarioService;
import com.instagram.service.IImagenService;
import com.instagram.service.ILikeService;
import com.instagram.service.IPublicacionService;
import com.instagram.service.ISeguidoService;
import com.instagram.service.ISeguidorService;
import com.instagram.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

//CONTROLADOR QUE GESTIONA LAS CUENTAS, LIKES, COMENTARIOS, NUEVAS PUBLICACIONES, SEGUIDORES, ETC.
@Controller
@RequestMapping("/cuenta")
@SuppressWarnings("rawtypes")
public class CuentaController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

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

	// METOGO QUE AGREGA UN COMENTARIO A UNA PUBLICACION
	@PostMapping("/agregarComentario/{id}")
	public String agragarComentario(@RequestParam String comentario, @PathVariable Integer id, HttpSession session) {
		if (comentario.equals("")) {
			return "redirect:/";
		} else {
			Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
			Publicacion publicacion = publicacionService.findById(id).get();
			Comentario nuevoComentario = new Comentario(usuario, LocalDate.now(), comentario, publicacion);
			comentarioService.save(nuevoComentario);
			return "redirect:/#post" + id;
		}
	}

	// METOGO QUE AGREGA UN LIKE A UNA PUBLICACION, O LO QUITA SI YA LO TIENE
	@GetMapping("/agregarQuitarLike/{id}")
	public String agragarQuitarLike(@PathVariable Integer id, HttpSession session) {
		Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		Publicacion publicacion = publicacionService.findById(id).get();
		Like like = new Like(usuario, publicacion);
		List<Like> likes = likeService.findAll();
		for (Like l : likes) {
			if (l.getUsuario().getUsername().equals(like.getUsuario().getUsername())
					&& l.getPublicacion().getId().equals(like.getPublicacion().getId())) {
				log.info("NO GUSTA MAS");
				likeService.delete(l.getId());
				return "redirect:/#post" + id;
			}
		}
		log.info("ME GUSTA");
		likeService.save(like);
		return "redirect:/#post" + id;
	}

	@PostMapping("/crearPublicacion")
	public String crearNuevaPublicacion(HttpSession session, Publicacion publicacion,
			@RequestParam("img") MultipartFile file) throws IOException {
		Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		publicacion.setUsuario(usuario);
		publicacion.setFechaCreacion(LocalDate.now());
		publicacionService.save(publicacion);

		BufferedImage bi = ImageIO.read(file.getInputStream());
		if (bi == null)
			return "redirect:/?img_error";
		Map result = cloudinarySevice.upload(file, "", "instagram/img_publicacion");// <------------------FALTA AGREGAR EFECTO
		Imagen imagenPublicacion = new Imagen((String) result.get("url"), (String) result.get("public_id"), usuario,
				publicacion);
		imagenService.save(imagenPublicacion);

		return "redirect:/#post" + publicacion.getId();
	}

	@PostMapping("/actualizarFotoPerfil")
	public String actualizarFotoDeFerfil(HttpSession session, @RequestParam("img") MultipartFile file,
			@RequestParam("efecto") String efecto) throws IOException {
		Usuario usuario = usuarioService.findById((Integer) session.getAttribute("idUsuario")).get();
		Imagen imgPerfilAct = usuario.getImgPerfil();

		BufferedImage bi = ImageIO.read(file.getInputStream());
		if (bi == null)
			return "redirect:/" + usuario.getUsername() + "/?img_error";
		Map result = cloudinarySevice.upload(file, efecto, "instagram/img_perfil");
		Imagen nuevaImgPerfil = new Imagen((String) result.get("url"), (String) result.get("public_id"), usuario, null);
		imagenService.save(nuevaImgPerfil);

		usuario.setImgPerfil(nuevaImgPerfil);
		usuarioService.update(usuario);

		imagenService.deleteById(imgPerfilAct.getId());
		if (!imgPerfilAct.getImgId().equals("hlqmcwbljw9ymsfopwkn")) {
			cloudinarySevice.delete(imgPerfilAct.getImgId());
		}
		return "redirect:/" + usuario.getUsername() + "/";
	}

	@PostMapping("/actualizarDatos")
	public String actualizarDatosDelUsuario(@RequestParam Integer id, @RequestParam String username,
			@RequestParam String password, @RequestParam String info1, @RequestParam String info2) {
		Usuario usuario = usuarioService.findById(id).get();
		if (!password.equals(""))
			usuario.setPassword(passEncoder.encode(password));
		List<Usuario> usuarios = usuarioService.findAll();
		usuarios.removeIf(u -> u.getUsername().equals(usuario.getUsername()));
		for (Usuario u : usuarios) {
			if (u.getUsername().equals(username))
				return "redirect:/" + usuario.getUsername() + "/?existe_u";
		}
		usuario.setUsername(username);
		usuario.setInfo1(info1);
		usuario.setInfo2(info2);
		usuarioService.update(usuario);
		return "redirect:/" + usuario.getUsername() + "/?act_e";
	}

	@GetMapping("/seguir/{id}")
	public String seguir(HttpSession session, @PathVariable("id") Integer id) {
		Optional<Usuario> usuarioLogueado = usuarioService.findById((Integer) session.getAttribute("idUsuario"));
		Optional<Usuario> usuarioVisitado = usuarioService.findById(id);
		if (usuarioLogueado.isPresent() && usuarioVisitado.isPresent()) {
			Seguidor logueadoSigueAVisitado = new Seguidor(usuarioLogueado.get(), usuarioVisitado.get());
			Seguido visitadoEsSeguidoPorLogueado = new Seguido(usuarioVisitado.get(), usuarioLogueado.get());
			seguidorService.save(logueadoSigueAVisitado);
			seguidoService.save(visitadoEsSeguidoPorLogueado);
		}
		return "redirect:/" + usuarioVisitado.get().getUsername() + "/";
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
		return "redirect:/" + usuarioVisitado.get().getUsername() + "/";
	}

}
