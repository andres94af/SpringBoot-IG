package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Imagen;

public interface IImagenRepository extends JpaRepository<Imagen, Integer>{

}
