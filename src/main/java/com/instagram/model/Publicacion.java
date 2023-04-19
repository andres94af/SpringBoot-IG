package com.instagram.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PUBLICACIONES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Publicacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	private Usuario usuario;
	private TipoDePublicacion tipo;
	@OneToMany(mappedBy = "publicacion")
	private List<Imagen> imagenes;
	private String ubicacion;
	private LocalDate fechaCreacion;
	@OneToMany(mappedBy = "publicacion")
	private List<Comentario> comentarios;
	@OneToMany(mappedBy = "publicacion")
	private List<Usuario> likes;
	@ManyToOne
	private Feed feed;

}
