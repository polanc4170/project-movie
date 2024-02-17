package org.project.movie;

import org.project.container.PostgresDocker;
import org.project.image.ImageDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.testcontainers.shaded.com.google.common.collect.Streams;

import java.util.Collections;
import java.util.Set;

@DataJpaTest
class MovieServiceTest extends PostgresDocker {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MovieRepository repository;

	@Autowired
	private MovieService service;

	@BeforeEach
	public void beforeEach () {
		service.deleteMovies();
	}

	//
	// Create
	//

	@Test
	public void addMovieEmpty () {
		Assertions.assertDoesNotThrow(() -> service.addMovie(
			new MovieDTO(0L, "Title", 2000, "Description", Collections.emptySet())
		));
	}

	@Test
	public void addMoviePresent () {
		service.addMovie(new MovieDTO(0L, "Title", 2000, "Description", Set.of(
			new ImageDTO("uuid-0", new Byte[8]),
			new ImageDTO("uuid-1", new Byte[8])
		)));

		Assertions.assertThrows(
			MovieAlreadyExistsException.class,
			() -> service.addMovie(
				new MovieDTO(0L, "Title", 2000, "Description", Collections.emptySet())
			)
		);
	}

	//
	// Read
	//

	@Test
	public void getMovieByIdEmpty () {
		Assertions.assertThrows(
			MovieNotFoundException.class,
			() -> service.getMovieById(0L)
		);
	}

	@Test
	public void getMovieByIdPresent () {
		MovieDTO dto = new MovieDTO(0L, "Title", 2000, "Description", Set.of(
			new ImageDTO("uuid-0", new Byte[8]),
			new ImageDTO("uuid-1", new Byte[8])
		));

		service.addMovie(dto);

		Assertions.assertEquals(1, service.getMovies().size());
		Assertions.assertDoesNotThrow(() -> {
			MovieDTO dbDto = service.getMovieById(dto.imdbId());

			Assertions.assertEquals(dto.imdbId(),        dbDto.imdbId());
			Assertions.assertEquals(dto.title(),         dbDto.title());
			Assertions.assertEquals(dto.year(),          dbDto.year());
			Assertions.assertEquals(dto.description(),   dbDto.description());
			Assertions.assertEquals(dto.images().size(), dbDto.images().size());

			Assertions.assertTrue(Streams.zip(
				  dto.images().stream(),
				dbDto.images().stream(),
				(x, y) -> x.uuid().equals(y.uuid())
			).allMatch(b -> b));
		});
	}

	//
	// Update
	//

	//
	// Delete
	//

	@Test
	public void deleteAll () {
		service.addMovie(new MovieDTO(0L, "Title-1", 2000, "Description-1", Collections.emptySet()));
		service.addMovie(new MovieDTO(1L, "Title-2", 2000, "Description-2", Collections.emptySet()));

		Assertions.assertFalse(service.getMovies().isEmpty());

		service.deleteMovies();

		Assertions.assertTrue(service.getMovies().isEmpty());
	}

}