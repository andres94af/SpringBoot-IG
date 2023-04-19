package com.instagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Autorizacion;
import com.instagram.repository.IAutorizacionRepository;

@Service
public class AutorizacionServiceImpl implements IAutorizacionService{

	@Autowired
	IAutorizacionRepository autorizacionRepo;
	
	@Override
	public Autorizacion save(Autorizacion autorizacion) {
		return autorizacionRepo.save(autorizacion);
	}

}
