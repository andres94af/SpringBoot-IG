package com.instagram.service;

import java.util.List;
import com.instagram.model.Seguido;

public interface ISeguidoService{
	
	List<Seguido> findAll();
	public void save (Seguido seguido);
	public void deleteById(Integer id);
}
