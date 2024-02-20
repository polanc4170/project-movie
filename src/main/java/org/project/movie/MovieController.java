package org.project.movie;

import org.project.image.exception.ImageAlreadyExistsException;
import org.project.image.exception.ImageNotFoundException;
import org.project.movie.exception.MovieAlreadyExistsException;
import org.project.movie.exception.MovieNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "movies")
public class MovieController {

	private final MovieService service;

	//
	// Create
	//

	@PostMapping(path = "", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addMovie (@RequestBody MovieDTO movie) {
		try {
			service.addMovie(movie);

			URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(movie.imdbId())
				.toUri();

			return ResponseEntity.status(CREATED).location(location).build();
		}
		catch (MovieAlreadyExistsException |
			   ImageAlreadyExistsException exception
		) {
			return ResponseEntity.status(CONFLICT).body(
				exception.getMessage()
			);
		}
	}

	//
	// Read
	//

	@GetMapping(path = "", produces = "application/json")
	public ResponseEntity<Object> getMovies (@RequestParam Map<String, String> parameters) {
		try {
			return ResponseEntity.status(OK).body(
				service.getMovies(parameters)
			);
		}
		catch (Exception exception) {
			return ResponseEntity.status(BAD_REQUEST).body(
				exception.getMessage()
			);
		}
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Object> getMovieByImdbId (@PathVariable Long id) {
		try {
			return ResponseEntity.status(OK).body(
				service.getMovieByImdbId(id)
			);
		}
		catch (MovieNotFoundException exception) {
			return ResponseEntity.status(BAD_REQUEST).body(
				exception.getMessage()
			);
		}
	}

	//
	// Update
	//

	@PutMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> updateMovieByImdbId (@PathVariable Long id, @RequestBody MovieDTO movie) {
		try {
			return ResponseEntity.status(OK).body(
				service.updateMovieByImdbId(id, movie)
			);
		}
		catch (MovieNotFoundException |
			   ImageNotFoundException exception
		) {
			return ResponseEntity.status(BAD_REQUEST).body(
				exception.getMessage()
			);
		}
	}

	//
	// Delete
	//

	@DeleteMapping(path = "", produces = "application/json")
	public ResponseEntity<Object> deleteMovies () {
		service.deleteMovies();

		return ResponseEntity.status(OK).build();
	}

	@DeleteMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Object> deleteMovieByImdbId (@PathVariable Long id) {
		try {
			service.deleteMovieByImdbId(id);

			return ResponseEntity.status(OK).build();
		}
		catch (MovieNotFoundException exception) {
			return ResponseEntity.status(BAD_REQUEST).body(
				exception.getMessage()
			);
		}
	}

}
