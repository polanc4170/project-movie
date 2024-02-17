package org.project.movie;

import org.project.image.ImageMapper;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MovieMapper {

	private final ImageMapper mapper;

	public MovieDTO toDTO (Movie movie) {
		return new MovieDTO(
			movie.getImdbId(),
			movie.getTitle(),
			movie.getYear(),
			movie.getDescription(),
			movie.getImages()
				.stream()
				.map(mapper::toDTO)
				.collect(Collectors.toSet())
		);
	}

	public Movie toMovie (MovieDTO dto) {
		Movie movie = new Movie();

		movie.setImdbId(dto.imdbId());
		movie.setTitle(dto.title());
		movie.setYear(dto.year());
		movie.setDescription(dto.description());
		movie.setImages(dto.images()
			.stream()
			.map(mapper::toImage)
			.collect(Collectors.toSet())
		);

		return movie;
	}

}
