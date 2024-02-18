package org.project.movie;

import org.project.image.ImageMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {

	private final MovieRepository repository;
	private final MovieMapper     movieMapper;
	private final ImageMapper     imageMapper;

	//
	// Create
	//

	public void addMovie (MovieDTO movie) {
		Optional<Movie> dbOptMovie = repository.findByImdbId(movie.imdbId());

		if (dbOptMovie.isPresent()) {
			throw new MovieAlreadyExistsException(
				"Movie IMDB_ID '%d' already exists.".formatted(movie.imdbId())
			);
		}

		repository.save(movieMapper.toMovie(movie));
	}

	//
	// Read
	//

	private Page<MovieDTO> getMoviesByPage (Map<String, String> parameters) {
		return repository.findAll(
			PageRequest.of(
				Integer.parseInt(parameters.getOrDefault("page", "0")),
				Integer.parseInt(parameters.getOrDefault("size", "10"))
			)
		).map(movieMapper::toDTO);
	}

	private List<MovieDTO> getMoviesByFilter (Map<String, String> parameters) {
		List<Movie> movies;
		String      pattern = null;

		if (parameters.containsKey("pattern")) {
			pattern = parameters.get("pattern");
		}

		if (parameters.containsKey("startYear") || parameters.containsKey("endYear")) {
			Integer startYear = Integer.parseInt(parameters.getOrDefault("startYear",    "0"));
			Integer endYear   = Integer.parseInt(parameters.getOrDefault(  "endYear", "9999"));

			if (pattern != null) movies = repository.filterByPatternAndYear(pattern, startYear, endYear);
			else                 movies = repository.filterByYear(startYear, endYear);
		}
		else {
			if (pattern != null) movies = repository.filterByPattern(pattern);
			else                 movies = repository.findAll();
		}

		return movies.stream().map(movieMapper::toDTO).toList();
	}

	public Iterable<MovieDTO> getMovies (Map<String, String> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			if (parameters.containsKey("page")) {
				return getMoviesByPage(parameters);
			}

			return getMoviesByFilter(parameters);
		}

		return repository.findAll().stream().map(movieMapper::toDTO).toList();
	}

	public MovieDTO getMovieById (Long id) {
		Optional<Movie> dbOptMovie = repository.findByImdbId(id);

		if (dbOptMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(id)
			);
		}

		return movieMapper.toDTO(dbOptMovie.get());
	}

	//
	// Update
	//

	public MovieDTO updateMovieById (Long id, MovieDTO movie) {
		Optional<Movie> dbOptMovie = repository.findByImdbId(id);

		if (dbOptMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(id)
			);
		}

		Movie dbMovie = dbOptMovie.get();

		if (movie.title() != null && !movie.title().equals(dbMovie.getTitle())) {
			dbMovie.setTitle(movie.title());
		}

		if (movie.year() != null && !movie.year().equals(dbMovie.getYear())) {
			dbMovie.setYear(movie.year());
		}

		if (movie.description() != null && !movie.description().equals(dbMovie.getDescription())) {
			dbMovie.setDescription(movie.description());
		}

		if (movie.images() != null && !movie.images().isEmpty()) {
			dbMovie.setImages(movie.images()
				.stream()
				.map(imageMapper::toImage)
				.toList()
			);
		}

		return movieMapper.toDTO(repository.save(dbMovie));
	}

	//
	// Delete
	//

	public void deleteMovies () {
		repository.deleteAll();
	}

	public void deleteMovieById (Long id) {
		Optional<Movie> dbOptMovie = repository.findByImdbId(id);

		if (dbOptMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(id)
			);
		}

		repository.delete(dbOptMovie.get());
	}

}
