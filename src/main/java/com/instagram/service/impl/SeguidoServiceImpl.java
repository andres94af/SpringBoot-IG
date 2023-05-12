package com.instagram.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.instagram.model.Seguido;
import com.instagram.model.Usuario;
import com.instagram.repository.ISeguidoRepository;
import com.instagram.service.ISeguidoService;

@Service
public class SeguidoServiceImpl implements ISeguidoService {

	@Autowired
	ISeguidoRepository seguidoRepo;

	@Override
	public List<Seguido> findAll() {
		return seguidoRepo.findAll();
	}

	@Override
	public void save(Seguido seguido) {
		seguidoRepo.save(seguido);
	}

	@Override
	public void deleteById(Integer id) {
		seguidoRepo.deleteById(id);
	}

	@Override
	public void nuevoSeguido(Usuario usuario1, Usuario usuario2) {
		Seguido seguido = new Seguido(usuario1, usuario2);
		save(seguido);
	}

}
