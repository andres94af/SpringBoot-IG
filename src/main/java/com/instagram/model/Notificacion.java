package com.instagram.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Usuario emisor;

	private TipoDeNotificacion tipo;
	
	@ManyToOne
	private Publicacion publicacion;
	
	@OneToOne
	private Solicitud solicitud;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;

	@ManyToOne
	private Usuario destinatario;

	private boolean recibida;

	public Notificacion() {
	}

	public Notificacion(Usuario emisor, TipoDeNotificacion tipo, LocalDate fecha, Usuario destinatario,
			boolean recibida, Solicitud solicitud) {
		this.emisor = emisor;
		this.tipo = tipo;
		this.fecha = fecha;
		this.destinatario = destinatario;
		this.recibida = recibida;
		this.solicitud = solicitud;
	}
	
	public Notificacion(Usuario emisor, TipoDeNotificacion tipo, LocalDate fecha, Usuario destinatario,
			boolean recibida, Publicacion publicacion) {
		this.emisor = emisor;
		this.tipo = tipo;
		this.fecha = fecha;
		this.destinatario = destinatario;
		this.recibida = recibida;
		this.publicacion = publicacion;
	}
	
	public Notificacion(Usuario emisor, TipoDeNotificacion tipo, LocalDate fecha, Usuario destinatario,
			boolean recibida) {
		this.emisor = emisor;
		this.tipo = tipo;
		this.fecha = fecha;
		this.destinatario = destinatario;
		this.recibida = recibida;
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

	public TipoDeNotificacion getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeNotificacion tipo) {
		this.tipo = tipo;
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

	public boolean isRecibida() {
		return recibida;
	}

	public void setRecibida(boolean recibida) {
		this.recibida = recibida;
	}
	

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	@Override
	public String toString() {
		return "Notificacion [El usuario " + emisor.getUsername() + ", le envio un " + tipo + ", el dia " + fecha
				+ ", al usuario " + destinatario.getUsername() + ". Fue recibida? " + recibida + "]";
	}

}
