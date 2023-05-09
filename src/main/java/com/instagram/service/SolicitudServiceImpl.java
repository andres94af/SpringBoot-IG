package com.instagram.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Solicitud;
import com.instagram.repository.iSolicitudRepository;

@Service
public class SolicitudServiceImpl implements ISolicitudService{
	
	@Autowired
	iSolicitudRepository solicitudRepo;

	@Override
	public Solicitud save(Solicitud solicitud) {
		return solicitudRepo.save(solicitud);
	}

	@Override
	public void update(Solicitud solicitud) {
		solicitudRepo.save(solicitud);
	}

	@Override
	public List<Solicitud> findAll() {
		return solicitudRepo.findAll();
	}

	@Override
	public Optional<Solicitud> findById(Integer id) {
		return solicitudRepo.findById(id);
	}

	@Override
	public void delete(Integer id) {
		solicitudRepo.deleteById(id);
	}

}
