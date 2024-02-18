package org.project.movie;

import org.project.image.ImageMapper;

import org.springframework.stereotype.Component;

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
				.toList()
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
			.toList()
		);

		return movie;
	}

}
