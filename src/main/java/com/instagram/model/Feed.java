package com.instagram.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FEED")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Feed {

	@Id
	@OneToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "feed")
	private List<Publicacion> publicaciones;
}
