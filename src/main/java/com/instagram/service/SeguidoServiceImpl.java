package com.instagram.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.instagram.model.Seguido;
import com.instagram.repository.ISeguidoRepository;

@Service
public class SeguidoServiceImpl implements ISeguidoService {
	
	@Autowired
	ISeguidoRepository seguidoRepo;

	@Override
	public List<Seguido> findAll() {
		return seguidoRepo.findAll();
	}

	@Override
	public void save(Seguido seguido) {
		seguidoRepo.save(seguido);
	}

	@Override
	public void deleteById(Integer id) {
		seguidoRepo.deleteById(id);
	}

}
