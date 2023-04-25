package com.instagram.service;

import java.util.List;
import java.util.Optional;

import com.instagram.model.Publicacion;
import com.instagram.model.Usuario;

public interface IPublicacionService {
	List<Publicacion> findAll();
	List<Publicacion> findByUsuario(Usuario usuario);
	Optional<Publicacion> findById(Integer id);
	Publicacion save(Publicacion publicacion);
	void delete(Publicacion publicacion);
	
}
