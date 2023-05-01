package com.instagram.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.instagram.model.Seguidor;
import com.instagram.repository.ISeguidorRepository;

@Service
public class SeguidorServiceImpl implements ISeguidorService {
	
	@Autowired
	ISeguidorRepository seguidorRepo;

	@Override
	public List<Seguidor> findAll() {
		return seguidorRepo.findAll();
	}

	@Override
	public void save(Seguidor seguidor) {
		seguidorRepo.save(seguidor);
	}

	@Override
	public void deleteById(Integer id) {
		seguidorRepo.deleteById(id);
	}

}
