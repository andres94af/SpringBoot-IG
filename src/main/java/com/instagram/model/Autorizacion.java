package com.instagram.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AUTORIZACIONES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Autorizacion {
	
	@Id
	@OneToOne
	private Usuario usuario;
	private boolean activo;
	private String rol;

}