package com.instagram.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMENTARIOS")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	private Usuario usuario;
	private LocalDate fechaComentario;
	private String mensaje;
	@ManyToOne
	private Publicacion publicacion;

	public Comentario() {
	}

	public Comentario(Usuario usuario, LocalDate fechaComentario, String mensaje, Publicacion publicacion) {
		this.usuario = usuario;
		this.fechaComentario = fechaComentario;
		this.mensaje = mensaje;
		this.publicacion = publicacion;
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

	public LocalDate getFechaComentario() {
		return fechaComentario;
	}

	public void setFechaComentario(LocalDate fechaComentario) {
		this.fechaComentario = fechaComentario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	@Override
	public String toString() {
		return "Comentario [publicacion=" + publicacion.getId() + ", fechaComentario=" + fechaComentario + ", usuario="
				+ usuario.getUsername() + ", mensaje=" + mensaje + "]";
	}

}
