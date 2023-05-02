package com.instagram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Autorizacion;
import com.instagram.model.Imagen;
import com.instagram.model.Seguido;
import com.instagram.model.Usuario;
import com.instagram.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	IUsuarioRepository usuarioRepo;

	@Autowired
	IAutorizacionService autorizacionService;

	@Autowired
	IImagenService imagenService;

	@Autowired
	ISeguidoService seguidoService;

	@Override
	public List<Usuario> findAll() {
		return usuarioRepo.findAll();
	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepo.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		usuarioRepo.save(usuario);
		Autorizacion autorizacion = new Autorizacion(usuario, true, "USER");
		String urlImgDefault = "https://res.cloudinary.com/da52tfqfk/image/upload/v1682857184/instagram/img_perfil/hlqmcwbljw9ymsfopwkn.jpg";
		String idImgDefault = "hlqmcwbljw9ymsfopwkn";
		Imagen imagenDefault = new Imagen(urlImgDefault, idImgDefault, usuario, null);
		autorizacionService.save(autorizacion);
		imagenService.save(imagenDefault);
		usuario.setAutorizacion(autorizacion);
		usuario.setImgPerfil(imagenDefault);
		return usuarioRepo.save(usuario);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepo.findByEmail(email);
	}

	@Override
	public Optional<Usuario> findByUsername(String username) {
		return usuarioRepo.findByUsername(username);
	}

	@Override
	public void update(Usuario usuario) {
		usuarioRepo.save(usuario);
	}

	@Override
	public void delete(Usuario usuario) {
		usuarioRepo.delete(usuario);
	}

	@Override
	public List<Usuario> findAllSeguidos(Usuario usuario) {
		List<Seguido> seguidos = seguidoService.findAll();
		List<Usuario> seguidosPorElUsuario = new ArrayList<>();
		for (Seguido s : seguidos) {
			if (s.getUsuario().equals(usuario)) {
				seguidosPorElUsuario.add(s.getNombre());
			}
		}
		return seguidosPorElUsuario;
	}

	@Override
	public List<Usuario> findAllNoSeguidos(Usuario usuario) {
		List<Usuario> usuarios = usuarioRepo.findAll();
		List<Seguido> seguidos = seguidoService.findAll();
		List<Usuario> seguidosPorElUsuario = new ArrayList<>();
		for (Seguido s : seguidos) {
			if (s.getUsuario().equals(usuario)) {
				seguidosPorElUsuario.add(s.getNombre());
			}
		}
		for (Usuario sXu : seguidosPorElUsuario) {
			usuarios.removeIf(u -> u.equals(sXu));
		}
		usuarios.removeIf(u -> u.equals(usuario));
		return usuarios;
	}

}
