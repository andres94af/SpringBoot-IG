package com.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.instagram.model.Notificacion;
import com.instagram.repository.iNotificacionRepository;

@Service
public class NotificacionServiceImpl implements INotificacionService {

	@Autowired
	iNotificacionRepository notificacionRepo;

	@Override
	public Notificacion save(Notificacion notificacion) {
		return notificacionRepo.save(notificacion);
	}

	@Override
	public void update(Notificacion notificacion) {
		notificacionRepo.save(notificacion);
	}

	@Override
	public List<Notificacion> findAll() {
		return notificacionRepo.findAll();
	}

	@Override
	public Optional<Notificacion> findById(Integer id) {
		return notificacionRepo.findById(id);
	}

	@Override
	public void delete(Integer id) {
		notificacionRepo.deleteById(id);
	}

}
