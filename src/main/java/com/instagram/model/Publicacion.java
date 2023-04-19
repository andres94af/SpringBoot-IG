package com.instagram.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PUBLICACIONES")
public class Publicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Usuario usuario;
	private TipoDePublicacion tipo;
	@OneToMany(mappedBy = "publicacion")
	private List<Imagen> imagenes;
	private String ubicacion;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaCreacion;
	@OneToMany(mappedBy = "publicacion")
	private List<Comentario> comentarios;
	@OneToMany(mappedBy = "publicacion")
	private List<Likes> likes;

	public Publicacion() {
		// TODO Auto-generated constructor stub
	}

	public Publicacion(Usuario usuario, TipoDePublicacion tipo, List<Imagen> imagenes, String ubicacion,
			LocalDate fechaCreacion, List<Comentario> comentarios, List<Likes> likes) {
		super();
		this.usuario = usuario;
		this.tipo = tipo;
		this.imagenes = imagenes;
		this.ubicacion = ubicacion;
		this.fechaCreacion = fechaCreacion;
		this.comentarios = comentarios;
		this.likes = likes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoDePublicacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDePublicacion tipo) {
		this.tipo = tipo;
	}

	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Likes> getLikes() {
		return likes;
	}

	public void setLikes(List<Likes> likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Publicacion [usuario=" + usuario + ", tipo=" + tipo + ", imagenes=" + imagenes + ", ubicacion="
				+ ubicacion + ", fechaCreacion=" + fechaCreacion + ", comentarios=" + comentarios + ", likes="
				+ likes.size() + "]";
	}

}
