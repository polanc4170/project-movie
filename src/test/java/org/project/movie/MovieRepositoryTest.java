package org.project.movie;

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
	public void beforeEach () {
		repository.deleteAll();
	}

	@Test
	public void findByIMDbEmpty () {
		Optional<Movie> dbOptMovie = repository.findByImdbId(0L);

		Assertions.assertTrue(dbOptMovie.isEmpty());
	}

	@Test
	public void findByIMDbPresent () {
		Movie movie = new Movie(null, 0L, "Title", 2000, "Description", List.of());

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
	public void filterByPatternEmpty () {
		List<Movie> dbMovies = repository.filterByPattern("");

		Assertions.assertTrue(dbMovies.isEmpty());
	}

	@Test
	public void filterByPatternPresent () {
		repository.deleteAll();

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

		Assertions.assertEquals(6, repository.filterByPattern("Star Wars" ).size());
		Assertions.assertEquals(3, repository.filterByPattern("Terminator").size());
		Assertions.assertEquals(9, repository.filterByPattern("a"         ).size());
		Assertions.assertEquals(0, repository.filterByPattern("y"         ).size());
	}

	@Test
	public void filterByYearEmpty () {
		repository.deleteAll();

		Assertions.assertEquals(0, repository.filterByYear(1970, 1980).size());
		Assertions.assertEquals(0, repository.filterByYear(1970, 1990).size());
		Assertions.assertEquals(0, repository.filterByYear(1970, 2000).size());
		Assertions.assertEquals(0, repository.filterByYear(1970, 2010).size());
	}

	@Test
	public void filterByYearPresent () {
		repository.deleteAll();

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

		Assertions.assertEquals(1, repository.filterByYear(1970, 1980).size());
		Assertions.assertEquals(4, repository.filterByYear(1970, 1990).size());
		Assertions.assertEquals(6, repository.filterByYear(1970, 2000).size());
		Assertions.assertEquals(9, repository.filterByYear(1970, 2010).size());
	}

}
