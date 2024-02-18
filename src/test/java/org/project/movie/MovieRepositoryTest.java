package org.project.movie;

import org.project.ObjectGenerator;
import org.project.container.PostgresDocker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class MovieRepositoryTest extends PostgresDocker {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MovieRepository repository;

	@BeforeEach
	public void clearDatabase () {
		repository.deleteAll();
	}

	@Test
	public void findByImdbIdEmpty () {
		Optional<Movie> dbOptMovie = repository.findByImdbId(0L);

		Assertions.assertTrue(dbOptMovie.isEmpty());
	}

	@Test
	public void findByImdbIdPresent () {
		Movie movie = ObjectGenerator.randomMovie();

		System.out.println(movie.getImdbId());
		repository.save(movie);

		Optional<Movie> dbOptMovie = repository.findByImdbId(movie.getImdbId());

		Assertions.assertTrue(dbOptMovie.isPresent());

		Movie dbMovie = dbOptMovie.get();

		Assertions.assertEquals(movie.getTitle(),         dbMovie.getTitle());
		Assertions.assertEquals(movie.getYear(),          dbMovie.getYear());
		Assertions.assertEquals(movie.getDescription(),   dbMovie.getDescription());
		Assertions.assertEquals(movie.getImages().size(), dbMovie.getImages().size());
	}

	@Test
	public void findByPatternEmpty () {
		List<Movie> dbMovies = repository.findByPattern("");

		Assertions.assertTrue(dbMovies.isEmpty());
	}

	@Test
	public void findByPatternPresent () {
		repository.saveAll(List.of(
			new Movie(null, 1L, "Star Wars I",   1999, "Description", List.of()),
			new Movie(null, 2L, "Star Wars II",  2002, "Description", List.of()),
			new Movie(null, 3L, "Star Wars III", 2005, "Description", List.of()),
			new Movie(null, 4L, "Star Wars IV",  1977, "Description", List.of()),
			new Movie(null, 5L, "Star Wars V",   1980, "Description", List.of()),
			new Movie(null, 6L, "Star Wars VI",  1983, "Description", List.of()),
			new Movie(null, 7L, "Terminator",    1984, "Description", List.of()),
			new Movie(null, 8L, "Terminator 2",  1991, "Description", List.of()),
			new Movie(null, 9L, "Terminator 3",  2003, "Description", List.of())
		));

		Assertions.assertEquals(6, repository.findByPattern("Star Wars" ).size());
		Assertions.assertEquals(3, repository.findByPattern("Terminator").size());
		Assertions.assertEquals(9, repository.findByPattern("a"         ).size());
		Assertions.assertEquals(0, repository.findByPattern("y"         ).size());
	}

	@Test
	public void findByYearEmpty () {
		Assertions.assertEquals(0, repository.findByYear(1970, 1980).size());
		Assertions.assertEquals(0, repository.findByYear(1970, 1990).size());
		Assertions.assertEquals(0, repository.findByYear(1970, 2000).size());
		Assertions.assertEquals(0, repository.findByYear(1970, 2010).size());
	}

	@Test
	public void findByYearPresent () {
		repository.saveAll(List.of(
			new Movie(null, 1L, "Star Wars I",   1999, "Description", List.of()),
			new Movie(null, 2L, "Star Wars II",  2002, "Description", List.of()),
			new Movie(null, 3L, "Star Wars III", 2005, "Description", List.of()),
			new Movie(null, 4L, "Star Wars IV",  1977, "Description", List.of()),
			new Movie(null, 5L, "Star Wars V",   1980, "Description", List.of()),
			new Movie(null, 6L, "Star Wars VI",  1983, "Description", List.of()),
			new Movie(null, 7L, "Terminator",    1984, "Description", List.of()),
			new Movie(null, 8L, "Terminator 2",  1991, "Description", List.of()),
			new Movie(null, 9L, "Terminator 3",  2003, "Description", List.of())
		));

		Assertions.assertEquals(1, repository.findByYear(1970, 1980).size());
		Assertions.assertEquals(4, repository.findByYear(1970, 1990).size());
		Assertions.assertEquals(6, repository.findByYear(1970, 2000).size());
		Assertions.assertEquals(9, repository.findByYear(1970, 2010).size());
	}

}
