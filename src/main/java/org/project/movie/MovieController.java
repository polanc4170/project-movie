package org.project.movie;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "movies")
public class MovieController {

	private final MovieService service;
	private final MovieMapper  mapper;

	//
	// Create
	//

	@PostMapping(path = "")
	public void addMovie (@RequestBody MovieDTO movie) {
		service.addMovie(mapper.toMovie(movie));
	}

	//
	// Read
	//

	@GetMapping(path = "")
	public List<MovieDTO> getAllMovies () {
		return service.getAllMovies()
			.stream()
			.map(mapper::toDTO)
			.toList();
	}

	@GetMapping(path = "view")
	public MovieDTO getMovieByImdbId (@RequestParam(name = "id") Long id) {
		return mapper.toDTO(service.getMovieByImdbId(id));
	}

	//
	// Update
	//

	@PutMapping(path = "")
	public MovieDTO updateMovie (@RequestBody MovieDTO movie) {
		return mapper.toDTO(service.updateMovie(mapper.toMovie(movie)));
	}

	//
	// Delete
	//

	@DeleteMapping(path = "")
	public void deleteMovieByImdbId (@RequestParam(name = "id") Long id) {
		service.deleteMovieByImdbId(id);
	}

}
