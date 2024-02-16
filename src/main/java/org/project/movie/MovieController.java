package org.project.movie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping(path = "")
	public void addNewMovie (@RequestBody MovieDTO movie) {
		service.addNewMovie(mapper.toMovie(movie));
	}

}
