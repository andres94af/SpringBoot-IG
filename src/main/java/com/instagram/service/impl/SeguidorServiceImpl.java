package com.instagram.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.instagram.model.Seguidor;
import com.instagram.model.Usuario;
import com.instagram.repository.ISeguidorRepository;
import com.instagram.service.ISeguidorService;

@Service
public class SeguidorServiceImpl implements ISeguidorService {

	@Autowired
	ISeguidorRepository seguidorRepo;

	@Override
	public List<Seguidor> findAll() {
		return seguidorRepo.findAll();
	}

	@Override
	public void save(Seguidor seguidor) {
		seguidorRepo.save(seguidor);
	}

	@Override
	public void deleteById(Integer id) {
		seguidorRepo.deleteById(id);
	}

	@Override
	public void nuevoSeguidor(Usuario usuario1, Usuario usuario2) {
		Seguidor seguidor = new Seguidor(usuario1, usuario2);
		save(seguidor);
	}

}
