package com.instagram.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Comentario;
import com.instagram.repository.IComentarioRepository;
import com.instagram.service.IComentarioService;

@Service
public class ComentarioServiceImpl implements IComentarioService{

	@Autowired
	IComentarioRepository comentarioRepo;
	
	@Override
	public List<Comentario> findAll() {
		return comentarioRepo.findAll();
	}

	@Override
	public Comentario save(Comentario comentario) {
		return comentarioRepo.save(comentario);
	}

	@Override
	public void delete(Comentario comentario) {
		comentarioRepo.delete(comentario);
	}

}
