package com.instagram.service;

import java.util.List;
import java.util.Optional;

import com.instagram.model.Usuario;

public interface IUsuarioService {
	
	List<Usuario> findAll();
	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);
	void update(Usuario usuario);
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByUsername(String username);
	void delete(Usuario usuario);
}