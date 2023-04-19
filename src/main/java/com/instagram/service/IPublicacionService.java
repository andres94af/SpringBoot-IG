package com.instagram.service;

import java.util.List;
import java.util.Optional;

import com.instagram.model.Publicacion;

public interface IPublicacionService {
	List<Publicacion> findAll();
	Optional<Publicacion> findById(Integer id);
	Publicacion save(Publicacion publicacion);
	void delete(Publicacion publicacion);
}
