package com.instagram.service;

import java.util.List;

import com.instagram.model.Seguidor;
import com.instagram.model.Usuario;

public interface ISeguidorService {
	List<Seguidor> findAll();

	public void save(Seguidor seguido);

	public void deleteById(Integer id);

	void nuevoSeguidor(Usuario usuario1, Usuario usuario2);

}
