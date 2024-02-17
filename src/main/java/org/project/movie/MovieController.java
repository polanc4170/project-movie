package org.project.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public void addMovie (@RequestBody MovieDTO movie) {
		service.addMovie(movie);
	}

	//
	// Read
	//

	@GetMapping(path = "")
	public ResponseEntity<?> getMovies () {
		return ResponseEntity.ok(service.getMovies());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getMovieById (@PathVariable Long id) {
		return ResponseEntity.ok(service.getMovieById(id));
	}

	//
	// Update
	//

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateMovieById (@PathVariable Long id, @RequestBody MovieDTO movie) {
		return ResponseEntity.ok(service.updateMovieById(id, movie));
	}

	//
	// Delete
	//

	@DeleteMapping(path = "")
	public void deleteMovies () {
		service.deleteMovies();
	}

	@DeleteMapping(path = "/{id}")
	public void deleteMovieById (@PathVariable Long id) {
		service.deleteMovieById(id);
	}

}
