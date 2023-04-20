package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Seguidor;

public interface ISeguidorRepository extends JpaRepository<Seguidor, Integer> {

}
