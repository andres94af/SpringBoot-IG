package com.instagram.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Usuario emisor;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	@ManyToOne
	private Usuario destinatario;

	private boolean aceptada;

	public Solicitud() {
	}

	public Solicitud(Usuario emisor, LocalDate fecha, Usuario destinatario, boolean aceptada) {
		this.emisor = emisor;
		this.fecha = fecha;
		this.destinatario = destinatario;
		this.aceptada = aceptada;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

	@Override
	public String toString() {
		return "Solicitud [emisor=" + emisor.getUsername() + ", fecha=" + fecha + ", destinatario=" + destinatario.getUsername() + ", aceptada="
				+ aceptada + "]";
	}

	
}
