package com.instagram.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LIKES")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	private Usuario usuario;
	@ManyToOne
	private Publicacion publicacion;

	public Like() {
	}

	public Like(Integer id, Usuario usuario, Publicacion publicacion) {
		this.id = id;
		this.usuario = usuario;
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

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	@Override
	public String toString() {
		return "Likes [usuario=" + usuario.getUsername() + ", publicacion=" + publicacion.getId() + "]";
	}

}
