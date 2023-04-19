package com.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Usuario;
import com.instagram.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	IUsuarioRepository usuarioRepo;

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

}
