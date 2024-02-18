package org.project.movie;

import org.project.image.Image;

import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Image> images = new ArrayList<>();

}
