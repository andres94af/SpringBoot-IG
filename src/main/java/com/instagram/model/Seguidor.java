package com.instagram.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SEGUIDORES")
public class Seguidor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Usuario nombre;
	@ManyToOne
	private Usuario usuario;

	public Seguidor() {
	}

	public Seguidor(Usuario nombre, Usuario usuario) {
		this.nombre = nombre;
		this.usuario = usuario;
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

	public Usuario getNombre() {
		return nombre;
	}

	public void setNombre(Usuario nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Seguidor [" + nombre.getUsername() + ", sigue a usuario= " + usuario.getUsername() + "]";
	}

}
