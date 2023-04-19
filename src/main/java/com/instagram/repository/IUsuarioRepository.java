package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{

}
