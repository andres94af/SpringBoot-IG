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

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String apellido, LocalDate fechaNacimiento, String genero, String email,
			String username, String password, Imagen imgPerfil, Autorizacion autorizacion,
			List<Publicacion> publicaciones) {
		super();
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento="
				+ fechaNacimiento + ", email=" + email + ", username=" + username + ", imgPerfil=" + imgPerfil + "]";
	}

}
