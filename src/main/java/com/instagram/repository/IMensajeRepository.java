package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Mensaje;

public interface IMensajeRepository extends JpaRepository<Mensaje, Integer>{

}
