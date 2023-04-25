package com.instagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Publicacion;
import com.instagram.model.Usuario;

public interface IPublicacionRepository extends JpaRepository<Publicacion, Integer>{

	List<Publicacion> findByUsuario(Usuario usuario);

}
