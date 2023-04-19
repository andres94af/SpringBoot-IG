package com.instagram.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "USUARIOS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String genero;
	private String email;
	private String username;
	private String password;
	@OneToOne
	private Imagen imgPerfil;
	@OneToOne
	private Autorizacion autorizacion;
	@OneToOne
	private Feed feed;
	@ManyToOne
	private Publicacion publicacion;

}
