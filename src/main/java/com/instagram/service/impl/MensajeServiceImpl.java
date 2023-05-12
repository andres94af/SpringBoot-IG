package com.instagram.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Mensaje;
import com.instagram.repository.IMensajeRepository;
import com.instagram.service.IMensajeService;

@Service
public class MensajeServiceImpl implements IMensajeService{

	@Autowired
	IMensajeRepository mensajeRepo;
	
	@Override
	public Mensaje save(Mensaje mensaje) {
		return mensajeRepo.save(mensaje);
	}

	@Override
	public void delete(Integer id) {
		mensajeRepo.deleteById(id);
	}

	@Override
	public Optional<Mensaje> findById(Integer id) {
		return mensajeRepo.findById(id);
	}

	@Override
	public List<Mensaje> findAll() {
		return mensajeRepo.findAll();
	}

}
