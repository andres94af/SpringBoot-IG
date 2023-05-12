package com.instagram.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Publicacion;
import com.instagram.model.Usuario;
import com.instagram.repository.IPublicacionRepository;
import com.instagram.service.IPublicacionService;
import com.instagram.service.IUsuarioService;

@Service
public class PublicacionesServiceImpl implements IPublicacionService {

	@Autowired
	IPublicacionRepository publicacionesRepo;

	@Autowired
	IUsuarioService usuarioService;

	@Override
	public List<Publicacion> findAll() {
		List<Publicacion> publicaciones = publicacionesRepo.findAll();
		Collections.sort(publicaciones, new Comparator<Publicacion>() {
			public int compare(Publicacion p1, Publicacion p2) {
				return p2.getId().compareTo(p1.getId());
			}
		});
		return publicaciones;
	}

	@Override
	public Optional<Publicacion> findById(Integer id) {
		return publicacionesRepo.findById(id);
	}

	@Override
	public Publicacion save(Publicacion publicacion) {
		return publicacionesRepo.save(publicacion);
	}

	@Override
	public void delete(Publicacion publicacion) {
		publicacionesRepo.delete(publicacion);
	}

	@Override
	public List<Publicacion> findByUsuario(Usuario usuario) {
		List<Publicacion> publicaciones = publicacionesRepo.findByUsuario(usuario);
		Collections.sort(publicaciones, new Comparator<Publicacion>() {
			public int compare(Publicacion p1, Publicacion p2) {
				return p2.getId().compareTo(p1.getId());
			}
		});
		return publicaciones;
	}

	@Override
	public List<Publicacion> publicacionesQueSigo(Usuario usuario) {
		List<Publicacion> publicaciones = publicacionesRepo.findAll();
		List<Usuario> seguidos = usuarioService.findAllSeguidos(usuario);
		seguidos.add(usuario);
		List<Publicacion> publicacionesQueSigo = new ArrayList<>();
		for (Usuario s : seguidos) {
			for (Publicacion p : publicaciones) {
				if (p.getUsuario().equals(s)) {
					publicacionesQueSigo.add(p);
				}
			}
		}
		Collections.sort(publicacionesQueSigo, new Comparator<Publicacion>() {
			public int compare(Publicacion p1, Publicacion p2) {
				return p2.getId().compareTo(p1.getId());
			}
		});
		return publicacionesQueSigo;
	}
}
