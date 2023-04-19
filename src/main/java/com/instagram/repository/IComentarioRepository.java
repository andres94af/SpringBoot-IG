package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Comentario;

public interface IComentarioRepository extends JpaRepository<Comentario, Integer>{

}
