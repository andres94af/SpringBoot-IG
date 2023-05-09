package com.instagram.service;

import java.util.List;
import com.instagram.model.Seguido;
import com.instagram.model.Usuario;

public interface ISeguidoService {

	List<Seguido> findAll();

	public void save(Seguido seguido);

	public void deleteById(Integer id);

	void nuevoSeguido(Usuario usuario1, Usuario usuario2);
}
