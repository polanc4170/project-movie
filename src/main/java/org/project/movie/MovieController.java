package org.project.movie;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "movies")
public class MovieController {

	private final MovieService service;

	//
	// Create
	//

	@PostMapping(path = "")
	public ResponseEntity<?> addMovie (@RequestBody MovieDTO movie) {
		service.addMovie(movie);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(null);
	}

	//
	// Read
	//

	@GetMapping(path = "")
	public ResponseEntity<?> getMovies (@RequestParam Map<String, String> parameters) {

		if (parameters == null || parameters.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(service.getMovies());
		}

		if (parameters.containsKey("page")) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(service.getMovies(PageRequest.of(
					Integer.parseInt(parameters.getOrDefault("page", "0")),
					Integer.parseInt(parameters.getOrDefault("size", "10"))
				)));
		}

		return ResponseEntity.status(HttpStatus.OK).body(service.getMovies(parameters));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getMovieById (@PathVariable Long id) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(service.getMovieById(id));
	}

	//
	// Update
	//

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateMovieById (@PathVariable Long id, @RequestBody MovieDTO movie) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(service.updateMovieById(id, movie));
	}

	//
	// Delete
	//

	@DeleteMapping(path = "")
	public ResponseEntity<?> deleteMovies () {
		service.deleteMovies();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(null);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteMovieById (@PathVariable Long id) {
		service.deleteMovieById(id);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(null);
	}

}
