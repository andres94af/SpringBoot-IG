package com.instagram.service;

import java.util.List;
import java.util.Optional;
import com.instagram.model.Mensaje;

public interface IMensajeService {
	Mensaje save(Mensaje mensaje);

	void delete(Integer id);

	Optional<Mensaje> findById(Integer id);

	List<Mensaje> findAll();
}
