package org.project.movie;

import org.project.image.Image;

import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NaturalId
	@Column(nullable = false, unique = true)
	private Long imdbId;

	@Column(nullable = false, length = 128)
	private String title;

	@Column(nullable = false)
	private Integer year;

	@Column(nullable = false)
	private String description;

	@OneToMany
	private Set<Image> images = new HashSet<>();

}
