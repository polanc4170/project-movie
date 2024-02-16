package org.project.movie;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {

	private final MovieRepository repository;

	//
	// Create
	//

	public void addMovie (Movie movie) {
		Optional<Movie> dbMovie = repository.findByImdbId(movie.getImdbId());

		if (dbMovie.isPresent()) {
			throw new MovieAlreadyExistsException(
				"Movie IMDB_ID '%d' already exists.".formatted(movie.getImdbId())
			);
		}

		repository.save(movie);
	}

	//
	// Read
	//

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

	//
	// Update
	//

	public Movie updateMovie (Movie movie) {
		Optional<Movie> dbMovie = repository.findByImdbId(movie.getImdbId());

		if (dbMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(movie.getImdbId())
			);
		}

		dbMovie.get().setTitle(movie.getDescription());
		dbMovie.get().setYear(movie.getYear());
		dbMovie.get().setDescription(movie.getDescription());
		dbMovie.get().setImages(movie.getImages());

		return repository.save(dbMovie.get());
	}

	//
	// Delete
	//

	public void deleteMovieByImdbId (Long id) {
		Optional<Movie> dbMovie = repository.findByImdbId(id);

		if (dbMovie.isEmpty()) {
			throw new MovieNotFoundException(
				"Movie IMDB_ID '%d' does not exist.".formatted(id)
			);
		}

		repository.delete(dbMovie.get());
	}

}
