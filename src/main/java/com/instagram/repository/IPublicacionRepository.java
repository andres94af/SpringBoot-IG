package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Publicacion;

public interface IPublicacionRepository extends JpaRepository<Publicacion, Integer>{

}
