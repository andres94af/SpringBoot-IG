package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.instagram.model.Solicitud;

public interface iSolicitudRepository extends JpaRepository<Solicitud, Integer> {

}
