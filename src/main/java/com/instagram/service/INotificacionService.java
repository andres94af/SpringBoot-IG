package com.instagram.service;

import java.util.List;
import com.instagram.model.Notificacion;

public interface INotificacionService {
	Notificacion save(Notificacion notificacion);

	void update(Notificacion notificacion);

	List<Notificacion> findAll();
}
