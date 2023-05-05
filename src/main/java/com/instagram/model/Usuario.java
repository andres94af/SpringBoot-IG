package com.instagram.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIOS")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String apellido;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaNacimiento;
	private String genero;
	private String info1;
	private String info2;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String username;
	private String password;
	@OneToOne
	private Imagen imgPerfil;
	@OneToOne
	private Autorizacion autorizacion;
	@OneToMany(mappedBy = "usuario")
	private List<Publicacion> publicaciones;
	@OneToMany(mappedBy = "usuario")
	private List<Seguidor> seguidores;
	@OneToMany(mappedBy = "usuario")
	private List<Seguido> seguidos;
	private boolean perfilPublico;
	@OneToMany(mappedBy = "destinatario")
	private List<Notificacion> notificaciones;

	public Usuario() {
	}

	public Usuario(String nombre, String apellido, LocalDate fechaNacimiento, String genero, String email,
			String username, String password, Imagen imgPerfil, Autorizacion autorizacion, boolean perfilPublico,
			List<Publicacion> publicaciones, List<Seguidor> seguidores, List<Seguido> seguidos, String info2,
			String info1, List<Notificacion> notificaciones) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.email = email;
		this.username = username;
		this.password = password;
		this.imgPerfil = imgPerfil;
		this.autorizacion = autorizacion;
		this.publicaciones = publicaciones;
		this.seguidores = seguidores;
		this.seguidos = seguidos;
		this.info1 = info1;
		this.info2 = info2;
		this.perfilPublico = perfilPublico;
		this.notificaciones = notificaciones;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Imagen getImgPerfil() {
		return imgPerfil;
	}

	public void setImgPerfil(Imagen imgPerfil) {
		this.imgPerfil = imgPerfil;
	}

	public Autorizacion getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(Autorizacion autorizacion) {
		this.autorizacion = autorizacion;
	}

	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	public List<Seguidor> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<Seguidor> seguidores) {
		this.seguidores = seguidores;
	}

	public List<Seguido> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(List<Seguido> seguidos) {
		this.seguidos = seguidos;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public boolean isPerfilPublico() {
		return perfilPublico;
	}

	public void setPerfilPublico(boolean perfilPublico) {
		this.perfilPublico = perfilPublico;
	}

	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", email=" + email + ", username="
				+ username + ", perfilPublico=" + perfilPublico + "]";
	}

}
