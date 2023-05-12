package com.instagram.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Imagen;
import com.instagram.model.Usuario;
import com.instagram.repository.IImagenRepository;
import com.instagram.service.IImagenService;

@Service
public class ImagenServiceImpl implements IImagenService{

	@Autowired
	IImagenRepository imagenRepo;
	
	@Override
	public List<Imagen> findAll() {
		return imagenRepo.findAll();
	}

	@Override
	public Optional<Imagen> findByUsuario(Usuario usuario) {
		return imagenRepo.findByUsuario(usuario);
	}

	@Override
	public Imagen save(Imagen imagen) {
		return imagenRepo.save(imagen);
	}

	@Override
	public void deleteById(Integer id) {
		imagenRepo.deleteById(id);;
	}

}
