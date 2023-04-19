package com.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Publicacion;
import com.instagram.repository.IPublicacionRepository;

@Service
public class PublicacionesServiceImpl implements IPublicacionService{
	
	@Autowired
	IPublicacionRepository publicacionesRepo;

	@Override
	public List<Publicacion> findAll() {
		return publicacionesRepo.findAll();
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

}
