package com.instagram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Imagen;
import com.instagram.model.Usuario;

public interface IImagenRepository extends JpaRepository<Imagen, Integer>{

	Optional<Imagen> findByUsuario(Usuario usuario);

}
