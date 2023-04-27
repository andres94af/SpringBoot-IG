package com.instagram.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Publicacion;
import com.instagram.model.Usuario;
import com.instagram.repository.IPublicacionRepository;

@Service
public class PublicacionesServiceImpl implements IPublicacionService{
	
	@Autowired
	IPublicacionRepository publicacionesRepo;

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
}
