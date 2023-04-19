package com.instagram.service;

import java.util.List;
import java.util.Optional;

import com.instagram.model.Imagen;
import com.instagram.model.Usuario;

public interface IImagenService {
	List<Imagen> findAll();
	Optional<Imagen> findByUsuario(Usuario usuario);
	Imagen save(Imagen imagen);
	void delete(Imagen imagen);
}
