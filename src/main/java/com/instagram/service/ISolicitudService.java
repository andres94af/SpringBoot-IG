package com.instagram.service;

import java.util.List;
import java.util.Optional;
import com.instagram.model.Solicitud;

public interface ISolicitudService {
	
	Solicitud save(Solicitud solicitud);

	void update(Solicitud solicitud);

	List<Solicitud> findAll();
	
	Optional<Solicitud> findById(Integer id);
	
	void delete(Integer id);
}
