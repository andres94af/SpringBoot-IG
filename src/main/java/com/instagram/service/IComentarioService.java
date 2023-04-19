package com.instagram.service;

import java.util.List;

import com.instagram.model.Comentario;

public interface IComentarioService {
	List<Comentario> findAll();
	Comentario save(Comentario comentario);
	void delete(Comentario comentario);
}
