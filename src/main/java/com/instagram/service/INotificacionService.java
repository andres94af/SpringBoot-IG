package com.instagram.service;

import java.util.List;
import java.util.Optional;

import com.instagram.model.Notificacion;

public interface INotificacionService {
	Notificacion save(Notificacion notificacion);

	void update(Notificacion notificacion);

	List<Notificacion> findAll();
	
	Optional<Notificacion> findById(Integer id);
	
	void delete(Integer id);
}
