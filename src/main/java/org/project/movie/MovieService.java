package org.project.movie;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {

	private final MovieRepository repository;

	public List<Movie> getAllMovies () {
		return repository.findAll();
	}

	public Movie getMovieByImdbId (Long id) {
		Optional<Movie> dbMovie = repository.findByImdbId(id);

		if (dbMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(id)
			);
		}

		return dbMovie.get();
	}

	public void addNewMovie (Movie movie) {
		Optional<Movie> dbMovie = repository.findByImdbId(movie.getImdbId());

		if (dbMovie.isPresent()) {
			throw new MovieAlreadyExistsException(
				"Movie IMDB_ID '%d' already exists.".formatted(movie.getImdbId())
			);
		}

		repository.save(movie);
	}

}
