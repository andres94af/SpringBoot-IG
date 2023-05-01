package com.instagram.service;
import java.util.List;

import com.instagram.model.Seguidor;

public interface ISeguidorService{
	List<Seguidor> findAll();
	public void save (Seguidor seguido);
	public void deleteById(Integer id);

}
